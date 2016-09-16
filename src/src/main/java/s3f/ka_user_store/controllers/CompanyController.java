package s3f.ka_user_store.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3f.ka_user_store.dtos.CompanyDto;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.CompanyRepository;
import s3f.ka_user_store.interfaces.UserRepository;
import s3f.ka_user_store.services.User.CreateUserAction;
import s3f.ka_user_store.services.company.CreateCompanyAction;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 * Created by MSBurger on 09.09.2016.
 */
@RestController
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/api/v1/user-store/company", method = RequestMethod.PUT)
    @ApiOperation(value = "Create a new company.", produces = "application/json",consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "User successful created", response = UserDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = "User is duplicate.", response = UserDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "User can't be saved.", response = UserDto.class)
    })
    public ResponseEntity<CompanyDto> create(@RequestHeader(value = "Authorization") String authorization,
                                             @RequestHeader(value = "CorrelationToken") String correlationToken,
                                             @RequestBody CompanyDto companyDto) {
        return (ResponseEntity<CompanyDto>) (new CreateCompanyAction()).doActionOnCompany(companyRepository, mongoTemplate, authorization, correlationToken, companyDto);
    }
}
