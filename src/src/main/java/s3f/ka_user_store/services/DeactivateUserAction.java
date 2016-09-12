package s3f.ka_user_store.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

/**
 * Created by MSBurger on 12.09.2016.
 */
@Service
public class DeactivateUserAction implements UserActions {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeactivateUserAction.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    @RequestMapping(value = "/api/v1/ka-user-store/activateUser", method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
                                                     @RequestHeader(value = "Authorization") String authorization,
                                                     @RequestHeader(value = "CorrelationToken") String correlationToken,
                                                     @RequestBody UserDto userDto) {
        LOGGER.info("Activation of the user was successfully");
        LOGGER.info(userDto.toString());

        try {
            UserDto userDtoTemp = mongoTemplate.findOne(new Query(Criteria.where("userId").is(userDto.getUserId())
                    .andOperator(Criteria.where("email").is(userDto.getEmail()))), UserDto.class);

            if (userDtoTemp == null) {
                LOGGER.info("User not found");
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
            LOGGER.info(userDtoTemp.toString());
            if (userDto.isActive() == userDtoTemp.isActive() && userDto.isActive()) {
                LOGGER.info("User is deactivated was not changed");
                return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
            }
            LOGGER.info(userDtoTemp.toString());
            mongoTemplate.save(userDto);
            LOGGER.info(userDto.toString());
            LOGGER.info("Deactivation of the user was successfully");
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Deactivation of the user has failed.", e);
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
