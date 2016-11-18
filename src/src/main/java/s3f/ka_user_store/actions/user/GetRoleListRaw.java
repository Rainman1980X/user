package s3f.ka_user_store.actions.user;

import java.util.List;

import org.apache.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.framework.logger.LoggerHelper;
import s3f.ka_user_store.enumns.EntryDefiniton;
import s3f.ka_user_store.enumns.UserRoles;

@Service
public class GetRoleListRaw {

    public ResponseEntity<List<EntryDefiniton>> doAction(String authorization, String correlationToken) {
        LoggerHelper.logData(Level.INFO, "Get role list", correlationToken, authorization,
                GetRoleListText.class.getName());
        return new ResponseEntity<List<EntryDefiniton>>(UserRoles.getUserRoleEntryList(), HttpStatus.OK);
    }
    
}
