package s3f.ka_user_store.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import s3f.framework.rest.DirectRestCallBuilder;
import s3f.framework.rest.interfaces.RestCallGet;
import s3f.framework.rest.interfaces.RestCallPost;
import s3f.framework.rest.interfaces.RestCallPut;
import s3f.ka_user_store.dtos.UserLDAPDto;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by snbuchho on 21.12.2016.
 */
@Service
public class LDAPUserService {

    private SecureRandom random = new SecureRandom();

    @Value("${datev.servicegateway.user}")
    private String user;

    @Value("${datev.servicegateway.password}")
    private String password;

    @Value("${datev.servicegateway.host}")
    private String serviceGatewayHost;

    @Autowired
    private DirectRestCallBuilder restCallBuilder;

    private String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }

    public UserLDAPDto createLDAPUser(UserLDAPDto userLDAPDto){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization",
                "Basic " + Base64Utils.encodeToString((user + ":" + password).getBytes(Charset.defaultCharset())));
        headers.put("Request-Id", nextSessionId());
        Map<String, String> uri = new HashMap<>();
        RestCallPost<UserLDAPDto, UserLDAPDto> restCallPost = restCallBuilder.buildPost(serviceGatewayHost, null,
                "/iam-integration/v1/persons", headers, uri, UserLDAPDto.class, userLDAPDto);
        ResponseEntity<UserLDAPDto> responseEntity = restCallPost.execute();
        return responseEntity.getBody();
    }

    public boolean changeUserPassword(String userId, String password) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization",
                "Basic " + Base64Utils.encodeToString((user + ":" + password).getBytes(Charset.defaultCharset())));
        headers.put("Request-Id", nextSessionId());
        Map<String, String> uri = new HashMap<>();
        uri.put("person-uuid",userId);
        RestCallGet<Map> restCallGet =  restCallBuilder.buildGet(serviceGatewayHost,null,"/iam-integration/v1/persons/{person-uuid}",headers,uri,Map.class);
        ResponseEntity<Map> responseEntity = restCallGet.execute();
        if(!responseEntity.getStatusCode().is2xxSuccessful()){
            throw new Exception("Could not retrieve User from LDAP");
        }
        Map<String,Object> userData = convertMapData(responseEntity.getBody());
        userData.put("password",password);
        RestCallPut<Map> restCallPut = restCallBuilder.buildPut(serviceGatewayHost, null,
                "/iam-integration/v1/persons", headers, uri,userData);
        ResponseEntity changePutResponse = restCallPut.execute();
        return changePutResponse.getStatusCode().is2xxSuccessful();
    }

    private Map<String,Object> convertMapData(Map data){
        Map<String,Object> convertedData = new HashMap<>();
        for(Object key:data.keySet()){
            convertedData.put(key.toString(),data.get(key));
        }
        return convertedData;
    }

}
