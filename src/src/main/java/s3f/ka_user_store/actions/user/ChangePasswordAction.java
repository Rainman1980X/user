package s3f.ka_user_store.actions.user;

import java.util.Map;

import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.framework.logger.LoggerHelper;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserRepository;

/**
 * Created by MSBurger on 12.09.2016.
 */
@Service
public class ChangePasswordAction {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    private String authorization;
    private String correlationToken;

    public ResponseEntity<HttpStatus> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
            String authorization, String correlationToken, Map<String, String> httpValues) {
        LoggerHelper.logData(Level.INFO, "Set a new password for the user", correlationToken, authorization,
                ChangePasswordAction.class.getName());
        try {
            UserDto userDtoTemp = mongoTemplate
                    .findOne(new Query(Criteria.where("userId").is(httpValues.get("userId"))), UserDto.class);

            if (userDtoTemp == null) {
                LoggerHelper.logData(Level.INFO, "user not found", correlationToken, authorization,
                        ChangePasswordAction.class.getName());
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
            if ((httpValues.get("password")).equals(userDtoTemp.getPassword())) {
                LoggerHelper.logData(Level.INFO, "Password was not changed", correlationToken, authorization,
                        ChangePasswordAction.class.getName());
                return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
            }
            mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(userDtoTemp.getUserId())),
                    Update.update("password", httpValues.get("password")), UserDto.class);
            LoggerHelper.logData(Level.INFO, "Password of the user successful changed", correlationToken, authorization,
                    ChangePasswordAction.class.getName());
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);

        } catch (Exception e) {
            LoggerHelper.logData(Level.INFO, "Password change fails.", correlationToken, authorization,
                    ChangePasswordAction.class.getName(), e);
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
