package s3f.ka_user_store.dtos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by snbuchho on 08.11.2016.
 */
@Component
public class UserTokenConfigDto {
    @Value("${datev.mock.sharedSecret}")
    private String secret;
    @Value("${datev.mock.issuer}")
    private String issuer;
    @Value("${datev.mock.audience}")
    private String audience;
    @Value("${datev.mock.lifeTimeInHours}")
    private int lifetime;

    public String getSecret() {
        return secret;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getAudience() {
        return audience;
    }

    public int getLifetime() {
        return lifetime;
    }
}
