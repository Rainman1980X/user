package s3f.ka_user_store.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserActions;
import s3f.ka_user_store.interfaces.UserRepository;

import java.util.Map;

/**
 * Created by MSBurger on 12.09.2016.
 */
public class GetUserAction implements UserActions<Map<String,String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetUserAction.class);

    @Override
    public ResponseEntity<UserDto> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
                                                  String authorization,
                                                  String correlationToken,
                                                  Map<String,String> httpValues) {
        UserDto userDtoTemp = userRepository.findOneByUserId(httpValues.get("userId"));
        if (userDtoTemp == null) {
            return new ResponseEntity<UserDto>(new UserDto(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDto>(userDtoTemp, HttpStatus.OK);

    }
}