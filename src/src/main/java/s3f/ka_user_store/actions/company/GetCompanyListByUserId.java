package s3f.ka_user_store.actions.company;

import java.util.List;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import s3f.framework.logger.LoggerHelper;
import s3f.ka_user_store.dtos.CompanyDto;

public class GetCompanyListByUserId {

    public ResponseEntity<List<CompanyDto>> doAction(MongoTemplate mongoTemplate, String authorization,
            String correlationToken, String userId) {
        LoggerHelper.logData(Level.INFO, "Company list which the userID contains", correlationToken, authorization,
                GetCompanyListByUserId.class.getName());
        List<CompanyDto> companyList = mongoTemplate
                .find(new Query(Criteria.where("assignedUserWithRole.userId").in(userId)),
                CompanyDto.class);
        if (companyList.isEmpty()) {
            LoggerHelper.logData(Level.WARN, "Company not found", correlationToken, authorization,
                    GetCompanyListByUserId.class.getName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(companyList, HttpStatus.OK);
    }
}
