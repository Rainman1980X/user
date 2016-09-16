package s3f.ka_user_store.services.User;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import s3f.Application;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserActions;
import s3f.ka_user_store.interfaces.UserRepository;
import s3f.ka_user_store.logging.LoggerHelper;

import java.util.UUID;

/**
 * Created by MSBurger on 12.09.2016.
 */
@Service
public class CreateUserAction implements UserActions<UserDto> {

    private UserRepository userRepository;

    @Override
    public ResponseEntity<UserDto> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
                                                     String authorization,
                                                     String correlationToken,
                                                     UserDto userDto) {
        this.userRepository = userRepository;
        LoggerHelper.logData(Level.INFO,"Create User",correlationToken,authorization, UserRepository.class.getName());
        if (hasDoubleEntry(userDto.getEmail())) {
            LoggerHelper.logData(Level.INFO,"User is duplicate.",correlationToken,authorization, UserRepository.class.getName());
            return new ResponseEntity<UserDto>(new UserDto(), HttpStatus.CONFLICT);
        }

        try {

            UserDto newUserDto = this.userRepository.save(userDto);
            LoggerHelper.logData(Level.INFO,"User successful created",correlationToken,authorization, UserRepository.class.getName());
            return new ResponseEntity<UserDto>(newUserDto,HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR,"User can't be saved",correlationToken,authorization, UserRepository.class.getName());
            return new ResponseEntity<UserDto>(new UserDto(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean hasDoubleEntry(String email) {
        return userRepository.findOneByEmail(email) != null;
    }
}
