package s3f.ka_user_store.services.User;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserActions;
import s3f.ka_user_store.interfaces.UserRepository;
import s3f.ka_user_store.logging.LoggerHelper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by MSBurger on 12.09.2016.
 */
@Service
public class ChangeRoleListAction implements UserActions<Map<String,String>> {
    @Override
    public ResponseEntity<HttpStatus> doActionOnUser(UserRepository userRepository, MongoTemplate mongoTemplate,
                                                     String authorization,
                                                     String correlationToken,
                                                     Map<String,String> httpValues) {
        LoggerHelper.logData(Level.INFO,"Change Role list of user",correlationToken,authorization, UserRepository.class.getName());
        try {
            UserDto userDtoTemp = mongoTemplate.findOne(new Query(Criteria.where("userId").is(httpValues.get("userId"))), UserDto.class);
            if (userDtoTemp == null) {
                LoggerHelper.logData(Level.INFO,"User not found",correlationToken,authorization, UserRepository.class.getName());
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
            userDtoTemp.getRoles().forEach(item->LoggerHelper.logData(Level.INFO,item,correlationToken,authorization, UserRepository.class.getName()));
            List<String> roleList = Stream.of(httpValues.get("roles").split(",")).collect(Collectors.toList());
            mongoTemplate.updateFirst(
                    new Query(Criteria.where("_id").is(userDtoTemp.getUserId())),
                    Update.update("roles", roleList), UserDto.class);
            LoggerHelper.logData(Level.INFO,"Role list of the user successful changed",correlationToken,authorization, UserRepository.class.getName());
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR,"Role list change fails.",correlationToken,authorization, UserRepository.class.getName(),e);
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
