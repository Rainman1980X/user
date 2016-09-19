package s3f.ka_user_store.actions.company;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import s3f.ka_user_store.actions.User.UserActions;
import s3f.ka_user_store.dtos.CompanyDto;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.CompanyRepository;
import s3f.ka_user_store.interfaces.UserRepository;
import s3f.ka_user_store.logging.LoggerHelper;

import java.util.Map;

/**
 * Created by MSBurger on 12.09.2016.
 */
public class GetCompanyAction implements CompanyActions<Map<String,String>> {

    @Override
    public ResponseEntity<CompanyDto> doActionOnCompany(CompanyRepository companyRepository, MongoTemplate mongoTemplate,
                                                     String authorization,
                                                     String correlationToken,
                                                     Map<String,String> httpValues) {
        CompanyDto companyDtoTemp = companyRepository.findOneByCompanyId(httpValues.get("companyId"));
        if (companyDtoTemp == null) {
            LoggerHelper.logData(Level.INFO,"Company not found",correlationToken,authorization, GetCompanyAction.class.getName());
            return new ResponseEntity<CompanyDto>(new CompanyDto(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CompanyDto>(companyDtoTemp, HttpStatus.OK);
    }
}
