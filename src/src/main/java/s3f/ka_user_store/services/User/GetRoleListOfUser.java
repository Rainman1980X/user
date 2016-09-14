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
public class GetRoleListOfUser implements UserActions<Map<String,String>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetRoleListOfUser.class);
    @Override
    public ResponseEntity<List<String>> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
                                                       String authorization,
                                                       String correlationToken,
                                                       Map<String,String> httpValues) {
        UserDto userDtoTemp = userRepository.findOneByUserId(httpValues.get("userId"));
        if(userDtoTemp == null){
            return new ResponseEntity<List<String>>(new ArrayList<String>(), HttpStatus.NOT_FOUND);
        }
        if(userDtoTemp.getRoles().isEmpty()){
            return new ResponseEntity<List<String>>(new ArrayList<String>(), HttpStatus.NOT_FOUND);
        }
        List<String> roleList = new ArrayList<String>();
        roleList.addAll(userDtoTemp.getRoles());
        return new ResponseEntity<List<String>>(roleList,HttpStatus.OK);
    }
}
