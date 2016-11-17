package s3f.ka_user_store.actions.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CreateUserCompanyJwtToken {

    public ResponseEntity<HttpStatus> doAction() {
        return new ResponseEntity<HttpStatus>(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
