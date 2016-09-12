package s3f.ka_user_store.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserActions;
import s3f.ka_user_store.interfaces.UserRepository;

/**
 * Created by MSBurger on 12.09.2016.
 */
@Service
public class CreateUserAction implements UserActions {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUserAction.class);

    private UserRepository userRepository;

    @Override
    public ResponseEntity<HttpStatus> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
                                                     @RequestHeader(value = "Authorization") String authorization,
                                                     @RequestHeader(value = "CorrelationToken") String correlationToken,
                                                     @RequestBody UserDto userDto) {
        this.userRepository = userRepository;
        LOGGER.info("Create User");
        LOGGER.info(userDto.toString());

        if (hasDoubleEntry(userDto.getEmail())) {
            LOGGER.error("User is duplicate.");
            return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
        }

        try {
            this.userRepository.save(userDto);
            LOGGER.info("User successful created");
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("User can't be saved", e);
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean hasDoubleEntry(String email) {
        return userRepository.findOneByEmail(email) != null;
    }
}
