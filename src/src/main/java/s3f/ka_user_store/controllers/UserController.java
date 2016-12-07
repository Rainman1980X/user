package s3f.ka_user_store.controllers;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import s3f.ka_user_store.actions.user.ActivateUserAction;
import s3f.ka_user_store.actions.user.ChangePasswordAction;
import s3f.ka_user_store.actions.user.ChangeRoleListAction;
import s3f.ka_user_store.actions.user.CreateUserAction;
import s3f.ka_user_store.actions.user.DeactivateUserAction;
import s3f.ka_user_store.actions.user.EditUserAction;
import s3f.ka_user_store.actions.user.GetAllUserAction;
import s3f.ka_user_store.actions.user.GetRoleListOfUser;
import s3f.ka_user_store.actions.user.GetRoleListRaw;
import s3f.ka_user_store.actions.user.GetRoleListText;
import s3f.ka_user_store.actions.user.GetUserAction;
import s3f.ka_user_store.actions.user.GetUserByEmailAction;
import s3f.ka_user_store.actions.user.GetUserByJWT;
import s3f.ka_user_store.actions.user.GetUserListByCompanyId;
import s3f.ka_user_store.actions.user.GetUserStatus;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.dtos.UserRoleDto;
import s3f.ka_user_store.enumns.EntryDefiniton;
import s3f.ka_user_store.interfaces.UserRepository;

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
    @ApiOperation(value = "Create a new user.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "User successful created", response = UserDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = "User is duplicate.", response = UserDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "User can't be saved.", response = UserDto.class) })
    public ResponseEntity<UserDto> create(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @RequestBody UserDto userDto) {
        return (new CreateUserAction()).doActionOnUser(userRepository, null, authorization, correlationToken, userDto);
    }

    @RequestMapping(value = "/api/v1/user-store/user", method = RequestMethod.POST)
    @ApiOperation(value = "Edit an user.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "User successful stored", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "User not found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "User unsuccessful stored", response = HttpStatus.class) })
    public ResponseEntity<HttpStatus> edit(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @RequestBody UserDto userDto) {
        return (new EditUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken,
                userDto);
    }

    @RequestMapping(value = "/api/v1/user-store/password/{userId}/{password}", method = RequestMethod.POST)
    @ApiOperation(value = "Change password of an user..", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Password of the user successful changed", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "User not found", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Password was not changed", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Password change fails.", response = HttpStatus.class) })
    public ResponseEntity<HttpStatus> changePassword(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable("userId") String userId,
            @PathVariable("password") String newPassword) {
        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("userId", userId);
        httpsValues.put("password", newPassword);
        return (new ChangePasswordAction()).doActionOnUser(userRepository, mongoTemplate, authorization,
                correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/user-store/role/{companyId}/{userId}/{roles}", method = RequestMethod.POST)
    @ApiOperation(value = "Change role of user.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Role list of the user successful changed", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "User not found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Role list change fails.", response = HttpStatus.class) })
    public ResponseEntity<HttpStatus> changeRole(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable("userId") String userId,
            @PathVariable("companyId") String companyId, @PathVariable("roles") String roles) {
        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("userId", userId);
        httpsValues.put("companyId", companyId);
        httpsValues.put("roles", roles);
        return (new ChangeRoleListAction()).doActionOnUser(userRepository, mongoTemplate, authorization,
                correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/user-store/status/{userId}/{status}", method = RequestMethod.POST)
    @ApiOperation(value = "Change status of user.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Activation / Deactivation of the user was successfullyd", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "User not found", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "User is activated or deactivated. ", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Activation / Deactivation of the user has failed.", response = HttpStatus.class) })
    public ResponseEntity<HttpStatus> changeStatus(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable("userId") String userId,
            @PathVariable("status") boolean status) {
        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("userId", userId);
        if (status) {
            return (new ActivateUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization,
                    correlationToken, httpsValues);
        }

        if (!status) {
            return (new DeactivateUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization,
                    correlationToken, httpsValues);
        }

        return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/api/v1/user-store/user/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get user by userID.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "User found", response = UserDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "User not found", response = UserDto.class) })
    public ResponseEntity<UserDto> getUser(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable("userId") String userId) {
        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("userId", userId);
        return (new GetUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken,
                httpsValues);
    }

    @RequestMapping(value = "/api/v1/user-store/user/email/{email:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "Get user by email.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "User found", response = UserDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "User not found", response = UserDto.class) })
    public ResponseEntity<UserDto> getUserByEmail(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable("email") String email) {
        return (new GetUserByEmailAction()).doActionOnUser(userRepository, authorization, correlationToken, email);
    }

    @RequestMapping(value = "/api/v1/user-store/user/me", method = RequestMethod.GET)
    @ApiOperation(value = "Get user based on JWT-Token.", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "User found", response = UserDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "User not found", response = UserDto.class) })
    public ResponseEntity<UserDto> getUserByJWT(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken) {
        return (new GetUserByJWT()).doAction(userRepository, authorization, correlationToken);
    }

    @RequestMapping(value = "/api/v1/user-store/user/list", method = RequestMethod.GET)
    @ApiOperation(value = "Get user list.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Users found", response = List.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Users not found", response = List.class) })
    public ResponseEntity<List<UserDto>> getUserList(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken) {
        Map<String, String> httpsValues = new HashMap<>();
        return (new GetAllUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken,
                httpsValues);
    }

    @RequestMapping(value = "/api/v1/user-store/user/list/{companyId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get user list.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Users found", response = List.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Users not found", response = List.class) })
    public ResponseEntity<List<UserDto>> GetUserListByCompanyId(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken,
            @PathVariable("companyId") String companyId) {
        return (new GetUserListByCompanyId()).doAction(mongoTemplate, authorization, correlationToken, companyId);
    }

    @RequestMapping(value = "/api/v1/user-store/{userId}/roles", method = RequestMethod.GET)
    @ApiOperation(value = "Get list of roles from user.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "List of roles created.", response = List.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "User not found.", response = List.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Role list is empty", response = List.class) })
    public ResponseEntity<List<UserRoleDto>> getRoleListOfUser(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable("userId") String userId) {
        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("userId", userId);
        return (new GetRoleListOfUser()).doActionOnUser(userRepository, authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/user-store/{userId}/status", method = RequestMethod.GET)
    @ApiOperation(value = "Get user status by userID.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Got user status ", response = Boolean.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Users not found", response = Boolean.class) })
    public ResponseEntity<Boolean> getUserStatus(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable("userId") String userId) {
        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("userId", userId);
        return (new GetUserStatus()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken,
                httpsValues);
    }

    @RequestMapping(value = "/api/v1/user-store/roles", method = RequestMethod.GET)
    @ApiOperation(value = "Get role list.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Get role list. ", response = List.class) })
    public ResponseEntity<List<String>> getRoleList(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken) {
        return (new GetRoleListText()).doActionOnUser(authorization, correlationToken);
    }

    @RequestMapping(value = "/api/v1/user-store/roles/rawlist", method = RequestMethod.GET)
    @ApiOperation(value = "Get role list.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Get role list. ", response = Map.class) })

    public ResponseEntity<List<EntryDefiniton>> getRoleRawList(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken) {
        return (new GetRoleListRaw()).doAction(authorization, correlationToken);
    }
}
