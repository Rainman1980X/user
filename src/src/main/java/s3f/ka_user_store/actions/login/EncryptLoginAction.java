package s3f.ka_user_store.actions.login;

import java.util.GregorianCalendar;

import org.apache.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import s3f.framework.encryption.EncryptionDecryptionService;
import s3f.framework.logger.LoggerHelper;

public class EncryptLoginAction {
    public ResponseEntity<String> doAction(String authorization, String correlationToken, String userId) {
        LoggerHelper.logData(Level.INFO, "Create encryption string", correlationToken, authorization,
                EncryptLoginAction.class.getName());
        String encryptString;
        String lifeTime = String.valueOf(GregorianCalendar.getInstance().getTimeInMillis());
        encryptString = EncryptionDecryptionService.encrypt(userId + ":" + lifeTime);
        if (encryptString == null) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
            LoggerHelper.logData(Level.INFO, "Create encryption fails", correlationToken, authorization,
                    EncryptLoginAction.class.getName());
        }
        LoggerHelper.logData(Level.INFO, "Encrytions created successfuly", correlationToken, authorization,
                EncryptLoginAction.class.getName());
        return new ResponseEntity<>(encryptString, HttpStatus.OK);
    }
}
