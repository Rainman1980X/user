package s3f.ka_user_store.actions.User;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import s3f.ka_user_store.enumns.UserRoles;
import s3f.ka_user_store.interfaces.UserRepository;
import s3f.ka_user_store.logging.LoggerHelper;

import java.util.List;

/**
 * Created by MSBurger on 19.09.2016.
 */
public class GetRoleList implements UserActions<Object> {

    @Override
    public ResponseEntity<List<String>> doActionOnUser(UserRepository userRepository,
                                                       MongoTemplate mongoTemplate,
                                                       String authorization,
                                                       String correlationToken,
                                                       Object httpValues) {
        LoggerHelper.logData(Level.INFO,"Get role list",correlationToken,authorization,GetRoleList.class.getName());
        return new ResponseEntity<List<String>>(UserRoles.getUserRoleList(), HttpStatus.OK);
    }
}