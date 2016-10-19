package s3f.ka_user_store.actions.company;

import java.util.Map;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.framework.logger.LoggerHelper;
import s3f.ka_user_store.dtos.CompanyDto;
import s3f.ka_user_store.interfaces.CompanyRepository;

/**
 * Created by MSBurger on 12.09.2016.
 */
@Service
public class GetCompanyAction {

    public ResponseEntity<CompanyDto> doActionOnCompany(CompanyRepository companyRepository,
            MongoTemplate mongoTemplate, String authorization, String correlationToken,
            Map<String, String> httpValues) {
        CompanyDto companyDtoTemp = companyRepository.findOneByCompanyId(httpValues.get("companyId"));
        if (companyDtoTemp == null) {
            LoggerHelper.logData(Level.INFO, "Company not found", correlationToken, authorization,
                    GetCompanyAction.class.getName());
            return new ResponseEntity<CompanyDto>(new CompanyDto(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CompanyDto>(companyDtoTemp, HttpStatus.OK);
    }
}
