package s3f.ka_user_store.actions.user;

import java.util.List;

import org.apache.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.framework.logger.LoggerHelper;
import s3f.ka_user_store.enumns.UserRoles;

/**
 * Created by MSBurger on 19.09.2016.
 */
@Service
public class GetRoleListText {

    public ResponseEntity<List<String>> doActionOnUser(String authorization, String correlationToken) {
        LoggerHelper.logData(Level.INFO, "Get role list", correlationToken, authorization, GetRoleListText.class.getName());
        return new ResponseEntity<List<String>>(UserRoles.getUserRoleList(), HttpStatus.OK);
    }
}
