package s3f.ka_user_store.services.User;

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

import java.util.Map;

/**
 * Created by MSBurger on 12.09.2016.
 */
@Service
public class ChangePasswordAction implements UserActions<Map<String, String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangePasswordAction.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    private String authorization;
    private String correlationToken;

    @Override
    public ResponseEntity<HttpStatus> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
                                                     String authorization,
                                                     String correlationToken,
                                                     Map<String, String> httpValues) {
        LOGGER.info("Set a new password for the User");
        try {
            UserDto userDtoTemp = mongoTemplate.findOne(new Query(Criteria.where("userId").is(httpValues.get("userId"))), UserDto.class);

            if (userDtoTemp == null) {
                LOGGER.info("User not found");
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
            LOGGER.info(userDtoTemp.toString());
            if ((httpValues.get("password")).equals(userDtoTemp.getPassword())) {
                LOGGER.info("Password was not changed");
                return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
            }
            LOGGER.info(userDtoTemp.toString());
            mongoTemplate.updateFirst(
                    new Query(Criteria.where("_id").is(userDtoTemp.getUserId())),
                    Update.update("password", httpValues.get("password")), UserDto.class);
            LOGGER.info("Password of the user successful changed");
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("Password change fails.", e);
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
