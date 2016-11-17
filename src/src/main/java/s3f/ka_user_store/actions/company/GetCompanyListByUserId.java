package s3f.ka_user_store.actions.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import s3f.framework.logger.LoggerHelper;
import s3f.ka_user_store.dtos.CompanyDto;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.dtos.UserRoleDto;

public class GetCompanyListByUserId {

    public ResponseEntity<List<CompanyDto>> doAction(MongoTemplate mongoTemplate, String authorization,
            String correlationToken, String userId) {
        LoggerHelper.logData(Level.INFO, "Company list which the userID contains", correlationToken, authorization,
                GetCompanyListByUserId.class.getName());
        List<UserDto> userList = mongoTemplate.find(new Query(Criteria.where("userId").is(userId)), UserDto.class);
        if (userList.isEmpty()) {
            LoggerHelper.logData(Level.WARN, "User not found", correlationToken, authorization,
                    GetCompanyListByUserId.class.getName());
        }

        List<String> companyIdList = ((List<UserRoleDto>) userList.stream().map(user -> user.getRoles())).stream()
                .map(role -> role.getCompanyId()).collect(Collectors.toCollection(ArrayList::new));

        List<CompanyDto> companyList = mongoTemplate
                .find(new Query(Criteria.where("assignedUserWithRole.userId").in(companyIdList)), CompanyDto.class);
        if (companyList.isEmpty()) {
            LoggerHelper.logData(Level.WARN, "Company not found", correlationToken, authorization,
                    GetCompanyListByUserId.class.getName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(companyList, HttpStatus.OK);
    }
}
