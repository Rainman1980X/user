package s3f.ka_user_store.services.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserActions;
import s3f.ka_user_store.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MSBurger on 12.09.2016.
 */
public class GetAllUserAction implements UserActions<Map<String,String>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetAllUserAction.class);

    @Override
    public ResponseEntity<List<UserDto>> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
                                                        String authorization,
                                                        String correlationToken,
                                                        Map<String,String> httpValues) {
        LOGGER.info("Get all User");
        List<UserDto> userList = userRepository.findAll();
        if(userList.isEmpty()){
            LOGGER.info("Now user found");
            return new ResponseEntity<List<UserDto>>(new ArrayList<UserDto>(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<UserDto>>(userList,HttpStatus.OK);
    }
}
