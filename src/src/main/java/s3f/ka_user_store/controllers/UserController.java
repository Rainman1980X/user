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
import s3f.ka_user_store.services.*;

/**
 * Created by MSBurger on 09.09.2016.
 */
@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/api/v1/ka-user-store/createUser", method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> create(@RequestHeader(value = "Authorization") String authorization,
                                             @RequestHeader(value = "CorrelationToken") String correlationToken,
                                             @RequestBody UserDto userDto) {
        return (new CreateUserAction()).doActionOnUser(userRepository,null,authorization,correlationToken,userDto);
    }

    @RequestMapping(value = "/api/v1/ka-user-store/editUser", method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> edit(@RequestHeader(value = "Authorization") String authorization,
                                             @RequestHeader(value = "CorrelationToken") String correlationToken,
                                             @RequestBody UserDto userDto) {
        return (new EditUserAction()).doActionOnUser(userRepository,mongoTemplate, authorization,correlationToken,userDto);
    }
    @RequestMapping(value = "/api/v1/ka-user-store/changePassword", method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> changePassword(@RequestHeader(value = "Authorization") String authorization,
                                             @RequestHeader(value = "CorrelationToken") String correlationToken,
                                             @RequestBody UserDto userDto) {
        return (new ChangePasswordAction()).doActionOnUser(userRepository,mongoTemplate, authorization,correlationToken,userDto);
    }

    @RequestMapping(value = "/api/v1/ka-user-store/changeRole", method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> changeRole(@RequestHeader(value = "Authorization") String authorization,
                                             @RequestHeader(value = "CorrelationToken") String correlationToken,
                                             @RequestBody UserDto userDto) {
        return (new ChangeRoleListAction()).doActionOnUser(userRepository,mongoTemplate, authorization,correlationToken,userDto);
    }

    @RequestMapping(value = "/api/v1/ka-user-store/activateUser", method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> activateUser(@RequestHeader(value = "Authorization") String authorization,
                                             @RequestHeader(value = "CorrelationToken") String correlationToken,
                                             @RequestBody UserDto userDto) {
        return (new ActivateUserAction()).doActionOnUser(userRepository,mongoTemplate, authorization,correlationToken,userDto);
    }
    @RequestMapping(value = "/api/v1/ka-user-store/deactivateUser", method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> deactivateUser(@RequestHeader(value = "Authorization") String authorization,
                                             @RequestHeader(value = "CorrelationToken") String correlationToken,
                                             @RequestBody UserDto userDto) {
        return (new DeactivateUserAction()).doActionOnUser(userRepository,mongoTemplate, authorization,correlationToken,userDto);
    }

}
