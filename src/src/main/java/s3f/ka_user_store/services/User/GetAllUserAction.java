package s3f.ka_user_store.services.User;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserActions;
import s3f.ka_user_store.interfaces.UserRepository;
import s3f.ka_user_store.logging.LoggerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MSBurger on 12.09.2016.
 */
public class GetAllUserAction implements UserActions<Map<String,String>> {

    @Override
    public ResponseEntity<List<UserDto>> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
                                                        String authorization,
                                                        String correlationToken,
                                                        Map<String,String> httpValues) {
        LoggerHelper.logData(Level.INFO,"Get all User",correlationToken,authorization, UserRepository.class.getName());
        List<UserDto> userList = userRepository.findAll();
        if(userList.isEmpty()){
            LoggerHelper.logData(Level.INFO,"No user found",correlationToken,authorization, GetAllUserAction.class.getName());
            return new ResponseEntity<List<UserDto>>(new ArrayList<UserDto>(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<UserDto>>(userList,HttpStatus.OK);
    }
}
