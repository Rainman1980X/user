package s3f.ka_user_store.actions.user;

import org.apache.log4j.Level;
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
public class GetUserByEmailAction {

    public ResponseEntity<UserDto> doActionOnUser(UserRepository userRepository, String authorization,
            String correlationToken, String email) {
        UserDto userDtoTemp = userRepository.findOneByEmail(email);
        if (userDtoTemp == null) {
            LoggerHelper.logData(Level.INFO, "user not found", correlationToken, authorization,
                    GetUserByEmailAction.class.getName());
            return new ResponseEntity<UserDto>(new UserDto(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDto>(userDtoTemp, HttpStatus.OK);

    }
}
