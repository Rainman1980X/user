package s3f.ka_user_store.actions.company;

import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import s3f.ka_user_store.dtos.CompanyDto;
import s3f.ka_user_store.interfaces.CompanyRepository;
import s3f.ka_user_store.interfaces.UserRepository;
import s3f.ka_user_store.logging.LoggerHelper;

/**
 * Created by MSBurger on 16.09.2016.
 */
public class EditCompanyAction implements CompanyActions<CompanyDto> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ResponseEntity<HttpStatus> doActionOnCompany(CompanyRepository companyRepository,
                                                        MongoTemplate mongoTemplate,
                                                        String authorization,
                                                        String correlationToken,
                                                        CompanyDto companyDto) {
        LoggerHelper.logData(Level.INFO,"Edit company",correlationToken,authorization, EditCompanyAction.class.getName());
        try {
            CompanyDto companyDtoTemp = mongoTemplate.findOne(new Query(Criteria.where("companyId").is(companyDto.getCompanyId())
                    .andOperator(Criteria.where("customerNumber").is(companyDto.getCustomerNumber()))), CompanyDto.class);
            if (companyDtoTemp == null) {
                LoggerHelper.logData(Level.INFO,"Company not found.",correlationToken,authorization, EditCompanyAction.class.getName());
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
            mongoTemplate.save(companyDto);
            LoggerHelper.logData(Level.INFO,"Company successful stored.",correlationToken,authorization, EditCompanyAction.class.getName());
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR,"Company unsuccessful stored",correlationToken,authorization, EditCompanyAction.class.getName(),e);
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
