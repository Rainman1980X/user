package s3f.ka_user_store.actions.login;

import java.util.GregorianCalendar;

import org.apache.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import s3f.framework.logger.LoggerHelper;
import s3f.framework.security.EncryptionDecryptionService;

public class DecryptLoginAction {
    private static final int PARAMETER_COUNT = 2;
    private static final int TIME_TO_LIFE = 1;
    private static final int DECRYPED_USERID = 0;
    private static final int TIME_TO_LIFE_LENGTH = 900000;

    public ResponseEntity<String> doAction(String authorization, String correlationToken, String encryptedValue) {
        LoggerHelper.logData(Level.INFO, "Dencrypt information start", correlationToken, authorization,
                DecryptLoginAction.class.getName());
        String decrypte = EncryptionDecryptionService.decrypt(encryptedValue);
        if (decrypte == null) {
            LoggerHelper.logData(Level.ERROR, "Dencryptions fails, because the encrypted value was not correct.",
                    correlationToken, authorization, DecryptLoginAction.class.getName());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        String[] information = decrypte.split(":");
        if (information.length != PARAMETER_COUNT) {
            LoggerHelper.logData(Level.ERROR, "Dencryptions fails, because the encrypted parameters were not correct.",
                    correlationToken, authorization, DecryptLoginAction.class.getName());
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        Long timeToLife = Long.valueOf(information[TIME_TO_LIFE]) + TIME_TO_LIFE_LENGTH;
        Long currentMillisecons = GregorianCalendar.getInstance().getTimeInMillis();
        if (currentMillisecons > timeToLife) {
            LoggerHelper.logData(Level.ERROR, "TTL is over.", correlationToken, authorization,
                    DecryptLoginAction.class.getName());
            return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
        }
        LoggerHelper.logData(Level.INFO, "Create encryption sucessful", correlationToken, authorization,
                DecryptLoginAction.class.getName());
        return new ResponseEntity<>(information[DECRYPED_USERID], HttpStatus.OK);

    }
}
