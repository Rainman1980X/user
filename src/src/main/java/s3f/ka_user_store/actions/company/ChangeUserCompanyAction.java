package s3f.ka_user_store.actions.company;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import s3f.ka_user_store.interfaces.CompanyRepository;


/**
 * Created by MSBurger on 12.09.2016.
 */
@Service
public class ChangeUserCompanyAction implements CompanyActions<Map<String, String>> {
    @Override
    public ResponseEntity<HttpStatus> doActionOnCompany(CompanyRepository companyRepository, MongoTemplate mongoTemplate,
                                                        String authorization,
                                                        String correlationToken,
                                                        Map<String, String> httpValues) {
        LoggerHelper.logData(Level.INFO, "Assigned user list", correlationToken, authorization, ChangeUserCompanyAction.class.getName());
        try {
            CompanyDto companyDtoTemp = mongoTemplate.findOne(new Query(Criteria.where("companyId").is(httpValues.get("companyId"))), CompanyDto.class);
            if (companyDtoTemp == null) {
                LoggerHelper.logData(Level.INFO, "Company not found", correlationToken, authorization, ChangeUserCompanyAction.class.getName());
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
            companyDtoTemp.getAssignedUserId().forEach(item -> LoggerHelper.logData(Level.INFO, item, correlationToken, authorization, ChangeUserCompanyAction.class.getName()));
            List<String> assignedUserIdList = Stream.of(httpValues.get("assignedUserList").split(",")).collect(Collectors.toList());
            //ToDo: Prüfung ob Unterliste user leer ist!!
            mongoTemplate.updateFirst(
                    new Query(Criteria.where("_id").is(companyDtoTemp.getCompanyId())),
                    Update.update("assignedUserId", assignedUserIdList), CompanyDto.class);
            LoggerHelper.logData(Level.INFO, "Assigned user list successful changed", correlationToken, authorization, ChangeUserCompanyAction.class.getName());
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR, "Assigned user list change fails.", correlationToken, authorization, ChangeUserCompanyAction.class.getName(), e);
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
