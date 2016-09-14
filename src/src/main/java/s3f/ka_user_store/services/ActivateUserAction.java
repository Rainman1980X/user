package s3f.ka_user_store.services;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserActions;
import s3f.ka_user_store.interfaces.UserRepository;

import java.util.Map;

/**
 * Created by MSBurger on 12.09.2016.
 */
@Service
public class ActivateUserAction implements UserActions<Map<String,String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivateUserAction.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<HttpStatus> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
                                                     String authorization,
                                                     String correlationToken,
                                                     Map<String,String> httpValues) {
        LOGGER.info("Activation of the user was successfully");
        try {
            UserDto userDtoTemp = mongoTemplate.findOne(new Query(Criteria.where("userId").is(httpValues.get("userId"))), UserDto.class);
            if (userDtoTemp == null) {
                LOGGER.info("User not found");
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
            LOGGER.info(userDtoTemp.toString());
            if (userDtoTemp.isActive() && !userDtoTemp.isActive()) {
                LOGGER.info("User is activated");
                return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
            }
            LOGGER.info(userDtoTemp.toString());
            mongoTemplate.updateFirst(
                    new Query(Criteria.where("_id").is(userDtoTemp.getUserId())),
                    Update.update("active", true), UserDto.class);
            LOGGER.info("Activation of the user was successfully");
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Activation of the user has failed.", e);
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
