package s3f.ka_user_store.interfaces;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;

/**
 * Created by MSBurger on 12.09.2016.
 */
public interface UserActions<T> {
    public ResponseEntity<?> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
                                                     String authorization,
                                                     String correlationToken,
                                                     T httpValues);

}
