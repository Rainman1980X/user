package s3f.ka_user_store.actions.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.framework.logger.LoggerHelper;
import s3f.ka_user_store.dtos.UserDto;

@Service
public class GetUserListByCompanyId {

    public ResponseEntity<List<UserDto>> doAction(MongoTemplate mongoTemplate,
            String authorization, String correlationToken, String companyId) {
        LoggerHelper.logData(Level.INFO, "Get all user", correlationToken, authorization,
                GetAllUserAction.class.getName());
        List<UserDto> userList = mongoTemplate.find(new Query(Criteria.where("roles.companyId").is(companyId)),
                UserDto.class);

        if (userList.isEmpty()) {
            LoggerHelper.logData(Level.INFO, "No user found", correlationToken, authorization,
                    GetAllUserAction.class.getName());
            return new ResponseEntity<List<UserDto>>(new ArrayList<UserDto>(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<UserDto>>(userList, HttpStatus.OK);
    }
}
