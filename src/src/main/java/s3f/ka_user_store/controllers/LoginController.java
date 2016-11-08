package s3f.ka_user_store.controllers;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import s3f.ka_user_store.actions.login.CreateUserJwtTokenAction;
import s3f.ka_user_store.actions.login.DecryptLoginAction;
import s3f.ka_user_store.actions.login.EncryptLoginAction;
import s3f.ka_user_store.actions.user.GetUserAction;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.dtos.UserTokenConfigDto;
import s3f.ka_user_store.interfaces.CompanyRepository;
import s3f.ka_user_store.interfaces.UserRepository;

@RestController
@RequestMapping(value = "/api/v1/user-store")
public class LoginController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserTokenConfigDto userTokenConfigDto;

    // /login/encrypt/{uuid}

    @RequestMapping(value = "/login/encrypt/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get encrypted String of user.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Encrytions created successfuly", response = String.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Create encryption fails", response = HttpStatus.class) })
    public ResponseEntity<String> getUserEncrypt(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable("userId") String userId) {
        return (new EncryptLoginAction()).doAction(authorization, correlationToken, userId);
    }

    // /login/decrypt/{encryptedValue}

    @RequestMapping(value="/login/decrypt/{encryptedValue}",method=RequestMethod.GET)
    @ApiOperation(value="Get decrypted user.",produces="application/json",consumes="application/json")
    @ApiResponses(value={
            @ApiResponse(code=HttpURLConnection.HTTP_OK,message="User identified within the ttl",response=String.class),
            @ApiResponse(code=HttpURLConnection.HTTP_UNAUTHORIZED,message="Time to live is run out",response=HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_PRECON_FAILED, message = "Dencryptions fails, because the encrypted parameters were not correct.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Dencryptions fails, because the encrypted value was not correct.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_CLIENT_TIMEOUT, message = "TTL is over.", response = HttpStatus.class)
    })

    public ResponseEntity<String> getUserId(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken,
            @PathVariable("encryptedValue") String encryptedValue) {

        return (new DecryptLoginAction()).doAction(authorization, correlationToken, encryptedValue);
    }

    @RequestMapping(value = "/jwt/user/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "Create JWT token from UserID", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "User found", response = UserDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "User not found", response = UserDto.class) })
    public ResponseEntity<UserDto> getJwtByUserId(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable("userId") String userId) {
        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("userId", userId);
        return (new GetUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken,
                httpsValues);
    }

    @RequestMapping(value = "/jwt/login/jwtGen/{userEmail}/{userPwd}", method = RequestMethod.GET)
    @ApiOperation(value = "Create JWT token from UserEmail", produces = "text/plain")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Password is correct and JWT generated", response = String.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "User not found, Password not defined or Passwords do not match", response = String.class) })
    public ResponseEntity<String> getJwtByUserLogin(@RequestHeader("Authorization") String authorization,@RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable("userEmail") String userEmail,@PathVariable("userPwd") String userPwd) {
        return (new CreateUserJwtTokenAction().doAction(userRepository, userTokenConfigDto,userEmail,userPwd));
    }
    // /jwt/create-user/{uuid}?company=companyId

    // /jwt/create-user-company/{uuid}/{companyId}

}
