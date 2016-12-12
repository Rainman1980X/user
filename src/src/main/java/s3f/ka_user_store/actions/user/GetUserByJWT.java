package s3f.ka_user_store.actions.user;

import java.text.ParseException;

import org.apache.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.framework.authorization.jwt.utility.JWTParserUtility;
import s3f.framework.logger.LoggerHelper;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserRepository;

/**
 * Created by snbuchho on 07.12.2016.
 */

@Service
public class GetUserByJWT {

    public ResponseEntity<UserDto> doAction(UserRepository userRepository, String authorization,
                                                  String correlationToken) {
        String userId=null;
        try {
            userId = JWTParserUtility.getIdFromToken(JWTParserUtility.parseJWT(authorization));
        } catch (ParseException e) {
            LoggerHelper.logData(Level.ERROR,"Could not extract UserId from JWT",correlationToken, authorization,
                    GetUserByJWT.class.getName(),e);
        }
        UserDto userDtoTemp = userRepository.findOneByUserId(userId);
        if (userDtoTemp == null) {
            LoggerHelper.logData(Level.INFO, "user not found", correlationToken, authorization,
                    GetUserByJWT.class.getName());
            return new ResponseEntity<UserDto>(new UserDto(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDto>(userDtoTemp, HttpStatus.OK);

    }

}



