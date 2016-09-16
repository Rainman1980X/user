package s3f.ka_user_store.actions.company;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import s3f.ka_user_store.interfaces.CompanyRepository;

/**
 * Created by MSBurger on 16.09.2016.
 */
public interface CompanyActions <T> {

    public ResponseEntity<?> doActionOnCompany(CompanyRepository companyRepository, MongoTemplate mongoTemplate,
                                               String authorization,
                                               String correlationToken,
                                               T httpValues);
}
