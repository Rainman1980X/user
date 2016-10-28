package s3f.ka_user_store.actions.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DecryptLoginAction {
    public ResponseEntity<HttpStatus> doAction() {
        return new ResponseEntity<HttpStatus>(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
