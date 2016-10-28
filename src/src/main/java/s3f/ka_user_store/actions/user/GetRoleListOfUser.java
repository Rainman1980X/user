package s3f.ka_user_store.actions.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class GetRoleListOfUser {

    public ResponseEntity<List<String>> doActionOnUser(UserRepository userRepository,
            String authorization, String correlationToken, Map<String, String> httpValues) {
        UserDto userDtoTemp = userRepository.findOneByUserId(httpValues.get("userId"));
        if (userDtoTemp == null) {
            LoggerHelper.logData(Level.INFO, "user not found", correlationToken, authorization,
                    GetRoleListOfUser.class.getName());
            return new ResponseEntity<List<String>>(new ArrayList<String>(), HttpStatus.NOT_FOUND);
        }
        if (userDtoTemp.getRoles().isEmpty()) {
            LoggerHelper.logData(Level.INFO, "Role list is empty.", correlationToken, authorization,
                    GetRoleListOfUser.class.getName());
            return new ResponseEntity<List<String>>(new ArrayList<String>(), HttpStatus.NOT_FOUND);
        }
        List<String> roleList = new ArrayList<String>();
        roleList.addAll(userDtoTemp.getRoles());
        LoggerHelper.logData(Level.INFO, "List of roles created.", correlationToken, authorization,
                GetRoleListOfUser.class.getName());
        return new ResponseEntity<List<String>>(roleList, HttpStatus.OK);
    }
}
