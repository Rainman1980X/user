package s3f.ka_user_store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import s3f.framework.jwt.web.config.JWTIgnoreURL;

import javax.annotation.PostConstruct;

/**
 * Created by snbuchho on 08.11.2016.
 */
@Component
public class UserStoreIgnoreUrls {

    @Autowired
    public UserStoreIgnoreUrls(JWTIgnoreURL filter){
        filter.addUrlToIgnore("/api/v1/user-store/jwt/login/jwtGen");
    }


}
