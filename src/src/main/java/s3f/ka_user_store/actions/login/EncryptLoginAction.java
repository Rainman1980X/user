package s3f.ka_user_store.actions.login;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserRepository;

public class EncryptLoginAction {
    public ResponseEntity<UserDto> doAction(UserRepository userRepository, MongoTemplate mongoTemplate,
            String authorization,
            String correlationToken, String userId) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
