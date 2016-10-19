package s3f.ka_user_store.actions.user;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
public class EditUserAction {

    public ResponseEntity<HttpStatus> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
            String authorization, String correlationToken, UserDto userDto) {
        LoggerHelper.logData(Level.INFO, "Edit user", correlationToken, authorization, EditUserAction.class.getName());
        try {
            UserDto userDtoTemp = mongoTemplate.findOne(new Query(Criteria.where("userId").is(userDto.getUserId())
                    .andOperator(Criteria.where("email").is(userDto.getEmail()))), UserDto.class);
            if (userDtoTemp == null) {
                LoggerHelper.logData(Level.INFO, "user not found.", correlationToken, authorization,
                        EditUserAction.class.getName());
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
            mongoTemplate.save(userDto);
            LoggerHelper.logData(Level.INFO, "user successful stored.", correlationToken, authorization,
                    EditUserAction.class.getName());
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR, "user unsuccessful stored", correlationToken, authorization,
                    EditUserAction.class.getName(), e);
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
