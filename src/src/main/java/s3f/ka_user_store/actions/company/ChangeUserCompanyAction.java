package s3f.ka_user_store.actions.company;

import java.util.List;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.framework.logger.LoggerHelper;
import s3f.ka_user_store.dtos.CompanyDto;
import s3f.ka_user_store.dtos.UserRoleDto;
import s3f.ka_user_store.interfaces.CompanyRepository;

/**
 * Created by MSBurger on 12.09.2016.
 */
@Service
public class ChangeUserCompanyAction {

    public ResponseEntity<HttpStatus> doActionOnCompany(CompanyRepository companyRepository,
            MongoTemplate mongoTemplate, String authorization, String correlationToken,
            String companyId, List<UserRoleDto> assignedUserWithRoles) {
        LoggerHelper.logData(Level.INFO, "Assigned user list", correlationToken, authorization,
                ChangeUserCompanyAction.class.getName());
        try {
            CompanyDto companyDtoTemp = mongoTemplate
                    .findOne(new Query(Criteria.where("companyId").is(companyId)), CompanyDto.class);
            if (companyDtoTemp == null) {
                LoggerHelper.logData(Level.INFO, "Company not found", correlationToken, authorization,
                        ChangeUserCompanyAction.class.getName());
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
            mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(companyDtoTemp.getCompanyId())),
                    Update.update("assignedUserWithRole", assignedUserWithRoles), CompanyDto.class);

            LoggerHelper.logData(Level.INFO, "Assigned user list successful changed", correlationToken, authorization,
                    ChangeUserCompanyAction.class.getName());
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR, "Assigned user list change fails.", correlationToken, authorization,
                    ChangeUserCompanyAction.class.getName(), e);
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
