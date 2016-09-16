package s3f.ka_user_store.services.company;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import s3f.ka_user_store.dtos.CompanyDto;
import s3f.ka_user_store.interfaces.CompanyActions;
import s3f.ka_user_store.interfaces.CompanyRepository;
import s3f.ka_user_store.logging.LoggerHelper;
import s3f.ka_user_store.services.User.CreateUserAction;

import java.util.UUID;

/**
 * Created by MSBurger on 16.09.2016.
 */
public class CreateCompanyAction implements CompanyActions<CompanyDto> {

    private CompanyRepository companyRepository;

    @Override
    public ResponseEntity<CompanyDto> doActionOnCompany(CompanyRepository companyRepository,
                                               MongoTemplate mongoTemplate,
                                               String authorization,
                                               String correlationToken,
                                               CompanyDto companyDto) {
        this.companyRepository = companyRepository;
        LoggerHelper.logData(Level.INFO,"Create company",correlationToken,authorization, CreateUserAction.class.getName());
        if (hasDoubleEntry(companyDto.getCustomerNumber())) {
            LoggerHelper.logData(Level.INFO,"Company is duplicate.",correlationToken,authorization, CreateUserAction.class.getName());
            return new ResponseEntity<CompanyDto>(new CompanyDto(), HttpStatus.CONFLICT);
        }

        try {
            companyDto.setCompanyId(UUID.randomUUID().toString());
            CompanyDto newCompanyDto = companyRepository.save(companyDto);
            LoggerHelper.logData(Level.INFO,"Company successful created",correlationToken,authorization, CreateCompanyAction.class.getName());
            return new ResponseEntity<CompanyDto>(newCompanyDto,HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR,"Company can't be saved",correlationToken,authorization, CreateCompanyAction.class.getName());
            return new ResponseEntity<CompanyDto>(new CompanyDto(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean hasDoubleEntry(String customerNumber) {
        return companyRepository.findOneByCustomerNumber(customerNumber) != null;
    }
}
