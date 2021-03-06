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
public class ActivateUserAction {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<HttpStatus> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
            String authorization, String correlationToken, Map<String, String> httpValues) {
        LoggerHelper.logData(Level.INFO, "Start activation of the user", correlationToken, authorization,
                ActivateUserAction.class.getName());

        try {
            UserDto userDtoTemp = mongoTemplate
                    .findOne(new Query(Criteria.where("userId").is(httpValues.get("userId"))), UserDto.class);
            if (userDtoTemp == null) {
                LoggerHelper.logData(Level.INFO, "user not found", correlationToken, authorization,
                        ActivateUserAction.class.getName());
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
            LoggerHelper.logData(Level.INFO, userDtoTemp.toString(), correlationToken, authorization,
                    ActivateUserAction.class.getName());
            if (userDtoTemp.isActive() && !userDtoTemp.isActive()) {
                LoggerHelper.logData(Level.INFO, "user is activated", correlationToken, authorization,
                        ActivateUserAction.class.getName());
                return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
            }
            mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(userDtoTemp.getUserId())),
                    Update.update("active", true), UserDto.class);
            LoggerHelper.logData(Level.INFO, "Activation of the user was successfully", correlationToken, authorization,
                    ActivateUserAction.class.getName());
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR, "Activation of the user has failed.", correlationToken, authorization,
                    ActivateUserAction.class.getName(), e);
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
