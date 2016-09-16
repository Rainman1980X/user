package s3f.ka_user_store.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import s3f.ka_user_store.dtos.CompanyDto;

/**
 * Created by MSBurger on 16.09.2016.
 */
public interface CompanyRepository extends MongoRepository<CompanyDto, String> {
    public CompanyDto findOneByCompanyId(String companyId);
    public CompanyDto findOneByCustomerNumber(String customerNumber);
}
