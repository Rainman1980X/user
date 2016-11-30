package s3f.ka_user_store.actions.login;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import net.minidev.json.JSONObject;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import s3f.framework.logger.LoggerHelper;
import s3f.ka_user_store.actions.user.GetUserByEmailAction;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.dtos.UserTokenConfigDto;
import s3f.ka_user_store.interfaces.UserRepository;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


public class CreateUserJwtTokenAction {

    public ResponseEntity<String> doAction(UserRepository repository, UserTokenConfigDto dto, String userEmail, String userPwd) {
        UserDto userDto = new GetUserByEmailAction().doActionOnUser(repository,"","",userEmail).getBody();
        if(userDto==null||userDto.getPassword()==null||!userDto.getPassword().equals(userPwd)){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("iss", URLDecoder.decode(dto.getIssuer()));
            claims.put("aud", dto.getAudience());
            claims.put("sub", userDto.getUserId());
            long time = System.currentTimeMillis();
            claims.put("iat", time / 1000);
            claims.put("nbf", time / 1000);
            claims.put("exp", time / 1000 + Math.abs(dto.getLifetime()) * 3600);
            JWSHeader headerTemp = JWSHeader.parse(dto.getSecret());
            JWSHeader header = new JWSHeader(headerTemp.getAlgorithm(), headerTemp.getType(), headerTemp.getContentType(), headerTemp.getCriticalParams(), headerTemp.getJWKURL(), headerTemp.getJWK(), headerTemp.getX509CertURL(), headerTemp.getX509CertThumbprint(), headerTemp.getX509CertSHA256Thumbprint(), headerTemp.getX509CertChain(), headerTemp.getKeyID(), null, headerTemp.getParsedBase64URL());
            JWSObject jwsObject = new JWSObject(header, new Payload(new JSONObject(claims)));
            JWSSigner signer = new RSASSASigner(RSAKey.parse(dto.getSecret()));
            jwsObject.sign(signer);
            //TODO Uncomment ot enable Datev-konform security
            LoggerHelper.logData(Level.ERROR,"Replace jwt with correct creation for Datev-Auth","","",CreateUserJwtTokenAction.class.toString());
            return new ResponseEntity<String>("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIiLCJyb2xlIjoiYWRtaW4iLCJuYmYiOjE0NzU2ODUwOTMsImlzcyI6InMzZiIsImV4cCI6NzI4OTU2NDQwMH0.ST8wpTTjC5nHAHlQUNXoUESi4pEGR1imv_Lv5bds8w8"/*jwsObject.serialize()*/,HttpStatus.OK);
        } catch (ParseException e) {
            LoggerHelper.logData(Level.ERROR,"Could not parse JwtHeader:"+e.getMessage(),"","",CreateUserJwtTokenAction.class.toString(),e);
        } catch (JOSEException e) {
            LoggerHelper.logData(Level.ERROR,"Could not parse SecretKey:"+e.getMessage(),"","",CreateUserJwtTokenAction.class.toString(),e);
        }
        return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
