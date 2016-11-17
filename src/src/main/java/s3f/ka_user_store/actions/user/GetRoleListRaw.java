package s3f.ka_user_store.actions.user;

import java.util.Map;

import org.apache.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.framework.logger.LoggerHelper;
import s3f.ka_user_store.enumns.UserRoles;

@Service
public class GetRoleListRaw {

    public ResponseEntity<Map<String, UserRoles>> doAction(String authorization, String correlationToken) {
        LoggerHelper.logData(Level.INFO, "Get role list", correlationToken, authorization,
                GetRoleListText.class.getName());
        return new ResponseEntity<Map<String, UserRoles>>(UserRoles.getUserRoleListMap(), HttpStatus.OK);
    }
    
}
