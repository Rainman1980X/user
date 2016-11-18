package s3f.ka_user_store.actions.user;

import java.util.ArrayList;
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
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.dtos.UserRoleDto;
import s3f.ka_user_store.enumns.UserRoles;
import s3f.ka_user_store.interfaces.UserRepository;

/**
 * Created by MSBurger on 12.09.2016.
 */
@Service
public class ChangeRoleListAction {

    public ResponseEntity<HttpStatus> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
            String authorization, String correlationToken, Map<String, String> httpValues) {
        LoggerHelper.logData(Level.INFO, "Change Role list of user", correlationToken, authorization,
                ChangeRoleListAction.class.getName());
        try {
            UserDto userDtoTemp = mongoTemplate
                    .findOne(new Query(Criteria.where("userId").is(httpValues.get("userId"))), UserDto.class);
            if (userDtoTemp == null) {
                LoggerHelper.logData(Level.INFO, "user not found", correlationToken, authorization,
                        ChangeRoleListAction.class.getName());
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }

            // ToDo: Pruefung ob Unterliste Role leer ist!!

            List<String> roleList = Stream.of(httpValues.get("roles").split(",")).collect(Collectors.toList());

            List<UserRoleDto> userRoleList = new ArrayList<>();
            for (String role : roleList) {
                userRoleList.add(new UserRoleDto(httpValues.get("companyId"), UserRoles.valueOf(role)));
            }

            mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(userDtoTemp.getUserId())),
                    Update.update("roles", userRoleList), UserDto.class);
            LoggerHelper.logData(Level.INFO, "Role list of the user successful changed", correlationToken,
                    authorization, ChangeRoleListAction.class.getName());
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR, "Role list change fails.", correlationToken, authorization,
                    ChangeRoleListAction.class.getName(), e);
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
