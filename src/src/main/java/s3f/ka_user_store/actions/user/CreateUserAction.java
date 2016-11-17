package s3f.ka_user_store.actions.user;

import java.util.UUID;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
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
public class CreateUserAction {

    private UserRepository userRepository;

    public ResponseEntity<UserDto> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
            String authorization, String correlationToken, UserDto userDto) {
        this.userRepository = userRepository;
        LoggerHelper.logData(Level.INFO, "Create user", correlationToken, authorization,
                CreateUserAction.class.getName());
        if (hasDoubleEntry(userDto.getEmail())) {
            LoggerHelper.logData(Level.INFO, "User is duplicate.", correlationToken, authorization,
                    CreateUserAction.class.getName());
            return new ResponseEntity<UserDto>(HttpStatus.CONFLICT);
        }

        try {
            userDto.setUserId(UUID.randomUUID().toString());
            UserDto newUserDto = this.userRepository.save(userDto);
            LoggerHelper.logData(Level.INFO, "User successful created", correlationToken, authorization,
                    CreateUserAction.class.getName());
            return new ResponseEntity<UserDto>(newUserDto, HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR, "User can't be saved", correlationToken, authorization,
                    CreateUserAction.class.getName());
            return new ResponseEntity<UserDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean hasDoubleEntry(String email) {
        return this.userRepository.findOneByEmail(email) != null;
    }
}
