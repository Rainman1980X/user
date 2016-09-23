package s3f.ka_user_store.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3f.ka_user_store.actions.user.*;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserRepository;
import sun.net.www.protocol.http.HttpURLConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MSBurger on 09.09.2016.
 */
@RestController

public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/api/v1/user-store/user", method = RequestMethod.PUT)
    @ApiOperation(value = "Create a new user.", produces = "application/json",consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "user successful created", response = UserDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = "user is duplicate.", response = UserDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "user can't be saved.", response = UserDto.class)
    })
    public ResponseEntity<UserDto> create(@RequestHeader(value = "Authorization") String authorization,
                                             @RequestHeader(value = "CorrelationToken") String correlationToken,
                                             @RequestBody UserDto userDto) {
        return (new CreateUserAction()).doActionOnUser(userRepository, null, authorization, correlationToken, userDto);
    }

    @RequestMapping(value = "/api/v1/user-store/user/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "Edit an user.", produces = "application/json",consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "user successful stored", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "user not found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "user unsuccessful stored", response = HttpStatus.class)
    })
    public ResponseEntity<HttpStatus> edit(@RequestHeader(value = "Authorization") String authorization,
                                           @RequestHeader(value = "CorrelationToken") String correlationToken,
                                           @RequestBody UserDto userDto) {
        return (new EditUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, userDto);
    }

    @RequestMapping(value = "/api/v1/user-store/password/{userId}/{password}", method = RequestMethod.POST)
    @ApiOperation(value = "Change password of an user..", produces = "application/json",consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Password of the user successful changed", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "user not found", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Password was not changed", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Password change fails.", response = HttpStatus.class)
    })
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
    @ApiOperation(value = "Change role of user.", produces = "application/json",consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Role list of the user successful changed", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "user not found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Role list change fails.", response = HttpStatus.class)
    })
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
    @ApiOperation(value = "Change status of user.", produces = "application/json",consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Activation / Deactivation of the user was successfullyd", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "user not found", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "user is activated or deactivated. ", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Activation / Deactivation of the user has failed.", response = HttpStatus.class)
    })
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
    @ApiOperation(value = "Get user by userID.", produces = "application/json",consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "user found", response = UserDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "user not found", response = UserDto.class)
    })
    public ResponseEntity<UserDto> getUser(@RequestHeader(value = "Authorization") String authorization,
                                           @RequestHeader(value = "CorrelationToken") String correlationToken,
                                           @PathVariable("userId") String userId) {
        Map<String,String> httpsValues = new HashMap<>();
        httpsValues.put("userId",userId);
        return (new GetUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/user-store/user/list", method = RequestMethod.GET)
    @ApiOperation(value = "Get user list.", produces = "application/json",consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Users found", response = List.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Users not found", response = List.class)
    })
    public ResponseEntity<List<UserDto>> getUserList(@RequestHeader(value = "Authorization") String authorization,
                                                     @RequestHeader(value = "CorrelationToken") String correlationToken) {
        Map<String,String> httpsValues = new HashMap<>();
        return (new GetAllUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/user-store/{userId}/roles", method = RequestMethod.GET)
    @ApiOperation(value = "Get list of roles from user.", produces = "application/json",consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "List of roles created.", response = List.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "user not found.", response = List.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Role list is empty", response = List.class)
    })
    public ResponseEntity<List<String>> getRoleListOfUser(@RequestHeader(value = "Authorization") String authorization,
                                                          @RequestHeader(value = "CorrelationToken") String correlationToken,
                                                          @PathVariable("userId") String userId) {
        Map<String,String> httpsValues = new HashMap<>();
        httpsValues.put("userId",userId);
        return (new GetRoleListOfUser()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, httpsValues);
    }
    @RequestMapping(value = "/api/v1/user-store/{userId}/status", method = RequestMethod.GET)
    @ApiOperation(value = "Get user status by userID.", produces = "application/json",consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Got user status ", response = Boolean.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Users not found", response = Boolean.class)
    })
    public ResponseEntity<Boolean> getUserStatus(@RequestHeader(value = "Authorization") String authorization,
                                                          @RequestHeader(value = "CorrelationToken") String correlationToken,
                                                          @PathVariable("userId") String userId) {
        Map<String,String> httpsValues = new HashMap<>();
        httpsValues.put("userId",userId);
        return (new GetUserStatus()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/user-store/roles", method = RequestMethod.GET)
    @ApiOperation(value = "Get role list.", produces = "application/json",consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Get role list. ", response = Boolean.class)
    })
    public ResponseEntity<List<String>> getRoleList(@RequestHeader(value = "Authorization") String authorization,
                                                 @RequestHeader(value = "CorrelationToken") String correlationToken) {
        return (new GetRoleList()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken, new Object());
    }
}
