package s3f.ka_user_store.services.User;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserActions;
import s3f.ka_user_store.interfaces.UserRepository;
import s3f.ka_user_store.logging.LoggerHelper;

import java.util.Map;

/**
 * Created by MSBurger on 12.09.2016.
 */
@Service
public class DeactivateUserAction implements UserActions<Map<String,String>> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ResponseEntity<HttpStatus> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
                                                     String authorization,
                                                     String correlationToken,
                                                     Map<String,String> httpValues) {
        LoggerHelper.logData(Level.INFO,"Deactivation of the user",correlationToken,authorization, DeactivateUserAction.class.getName());
        try {
            UserDto userDtoTemp = mongoTemplate.findOne(new Query(Criteria.where("userId").is(httpValues.get("userId"))), UserDto.class);
            if (userDtoTemp == null) {
                LoggerHelper.logData(Level.INFO,"User not found",correlationToken,authorization, DeactivateUserAction.class.getName());
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
            mongoTemplate.updateFirst(
                    new Query(Criteria.where("_id").is(userDtoTemp.getUserId())),
                    Update.update("active", false), UserDto.class);
            LoggerHelper.logData(Level.INFO,"Deactivation of the user was successfully",correlationToken,authorization, DeactivateUserAction.class.getName());
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR,"Deactivation of the user has failed.",correlationToken,authorization, DeactivateUserAction.class.getName(),e);
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
