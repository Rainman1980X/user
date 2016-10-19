package s3f.ka_user_store.actions.user;

import java.util.Map;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.framework.logger.LoggerHelper;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserRepository;

/**
 * Created by MSBurger on 14.09.2016.
 */
@Service
public class GetUserStatus {

    public ResponseEntity<Boolean> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
            String authorization, String correlationToken, Map<String, String> httpValues) {
        UserDto userDtoTemp = userRepository.findOneByUserId(httpValues.get("userId"));
        if (userDtoTemp == null) {
            LoggerHelper.logData(Level.INFO, "user not found", correlationToken, authorization,
                    GetUserStatus.class.getName());
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
        }
        LoggerHelper.logData(Level.INFO, "user status found.", correlationToken, authorization,
                GetUserStatus.class.getName());
        return new ResponseEntity<Boolean>(userDtoTemp.isActive(), HttpStatus.OK);
    }
}
