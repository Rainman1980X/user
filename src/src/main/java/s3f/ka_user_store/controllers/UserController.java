package s3f.ka_user_store.controllers;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserRepository;
import s3f.ka_user_store.services.User.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MSBurger on 09.09.2016.
 */
@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/api/v1/user-store/user", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> create(@RequestHeader(value = "Authorization") String authorization,
                                             @RequestHeader(value = "CorrelationToken") String correlationToken,
                                             @RequestBody UserDto userDto) {
        return (new CreateUserAction()).doActionOnUser(userRepository, null, authorization, correlationToken, userDto);
    }

    @RequestMapping(value = "/api/v1/user-store/user/{", method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> edit(@RequestHeader(value = "Authorization") String authorization,
                                           @RequestHeader(value = "CorrelationToken") String correlationToken,
                                           @RequestBody UserDto userDto) {
        return (new EditUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, userDto);
    }

    @RequestMapping(value = "/api/v1/user-store/password/{userId}/{password}", method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> changePassword(@RequestHeader(value = "Authorization") String authorization,
                                                     @RequestHeader(value = "CorrelationToken") String correlationToken,
                                                     @PathVariable("userId") String userId,
                                                     @PathVariable("password") String newPassword) {
        Map<String,String> httpsValues = new HashMap<>();
        httpsValues.put("userId",userId);
        httpsValues.put("password",newPassword);
        return (new ChangePasswordAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/user-store/role/{userId}/{roles}", method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> changeRole(@RequestHeader(value = "Authorization") String authorization,
                                                 @RequestHeader(value = "CorrelationToken") String correlationToken,
                                                 @PathVariable("userId") String userId,
                                                 @PathVariable("roles") List<String> roles) {
        Map<String,String> httpsValues = new HashMap<>();
        httpsValues.put("userId",userId);
        httpsValues.put("roles", String.join(",",roles));
        return (new ChangeRoleListAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/user-store/status/{userId}/{status}", method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> changeStatus(@RequestHeader(value = "Authorization") String authorization,
                                                   @RequestHeader(value = "CorrelationToken") String correlationToken,
                                                   @PathVariable("userId") String userId,
                                                   @PathVariable("status") boolean status) {
        Map<String,String> httpsValues = new HashMap<>();
        httpsValues.put("userId",userId);
        if(status) {
            return (new ActivateUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, httpsValues);
        }

        if(!status) {
            return (new DeactivateUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, httpsValues);
        }

        return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/api/v1/user-store/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser(@RequestHeader(value = "Authorization") String authorization,
                                           @RequestHeader(value = "CorrelationToken") String correlationToken,
                                           @PathVariable("userId") String userId) {
        Map<String,String> httpsValues = new HashMap<>();
        httpsValues.put("userId",userId);
        return (new GetUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/user-store/user/list", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getUserList(@RequestHeader(value = "Authorization") String authorization,
                                                     @RequestHeader(value = "CorrelationToken") String correlationToken) {
        Map<String,String> httpsValues = new HashMap<>();
        return (new GetAllUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/user-store/{userId}/roles", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getRoleListOfUser(@RequestHeader(value = "Authorization") String authorization,
                                                          @RequestHeader(value = "CorrelationToken") String correlationToken,
                                                          @PathVariable("userId") String userId) {
        Map<String,String> httpsValues = new HashMap<>();
        httpsValues.put("userId",userId);
        return (new GetRoleListOfUser()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, httpsValues);
    }
    @RequestMapping(value = "/api/v1/user-store/{userId}/status", method = RequestMethod.GET)
    public ResponseEntity<Boolean> getUserStatus(@RequestHeader(value = "Authorization") String authorization,
                                                          @RequestHeader(value = "CorrelationToken") String correlationToken,
                                                          @PathVariable("userId") String userId) {
        Map<String,String> httpsValues = new HashMap<>();
        httpsValues.put("userId",userId);
        return (new GetUserStatus()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, httpsValues);
    }
}
