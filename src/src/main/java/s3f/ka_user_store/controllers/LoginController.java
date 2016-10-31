package s3f.ka_user_store.controllers;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import s3f.ka_user_store.actions.login.EncryptLoginAction;
import s3f.ka_user_store.actions.user.GetUserAction;
import s3f.ka_user_store.dtos.UserDto;
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

    // /login/encrypt/{uuid}

    @RequestMapping(value = "/login/encrypt/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get encrypted String of user.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "User found", response = UserDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "User not found", response = UserDto.class) })
    public ResponseEntity<UserDto> getUserEncrypt(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable("userId") String userId) {
        return (new EncryptLoginAction()).doAction(userRepository, mongoTemplate, authorization, correlationToken,
                userId);
    }

    // /login/decrypt/{encryptedValue}

    @RequestMapping(value = "/login/decrypt/{encryptedValue}", method = RequestMethod.GET)
    @ApiOperation(value = "Get decrypted user.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "User found", response = UserDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "User not found", response = UserDto.class) })
    public ResponseEntity<UserDto> getUserDecrypt(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable("userId") String userId) {
        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("userId", userId);
        return (new GetUserAction()).doActionOnUser(userRepository, mongoTemplate, authorization, correlationToken,
                httpsValues);
    }

    @RequestMapping(value = "/jwt/user/{userID}", method = RequestMethod.GET)
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
    // /jwt/create-user/{uuid}?company=companyId

    // /jwt/create-user-company/{uuid}/{companyId}

}
