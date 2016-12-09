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
import s3f.framework.config.ServletConfig;
import s3f.ka_user_store.actions.company.EditCompanyAction;
import s3f.ka_user_store.actions.company.GetCompanyAction;
import s3f.ka_user_store.actions.company.GetCompanyListByUserId;
import s3f.ka_user_store.actions.company.GetTenantIdByIdAction;
import s3f.ka_user_store.dtos.CompanyDto;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.CompanyRepository;
import s3f.ka_user_store.interfaces.UserRepository;

/**
 * Created by MSBurger on 09.09.2016.
 */
@RestController
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    private final ServletConfig servletConfig;

    @Autowired
    private CreateCompanyController createCompanyController;

    @Autowired
    public CompanyController(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    @RequestMapping(value = "/api/v1/user-store/company", method = RequestMethod.PUT)
    @ApiOperation(value = "Create a new company.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Company successful created", response = CompanyDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = "Company is duplicate.", response = CompanyDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Company can't be saved.", response = CompanyDto.class) })
    public ResponseEntity<CompanyDto> create(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @RequestBody CompanyDto companyDto) {
        return createCompanyController.createCompany(authorization, correlationToken, companyDto);
    }

    @RequestMapping(value = "/api/v1/user-store/company/{companyId}", method = RequestMethod.POST)
    @ApiOperation(value = "Edit an user.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Company successful stored", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Company not found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Company unsuccessful stored", response = HttpStatus.class) })
    public ResponseEntity<HttpStatus> edit(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @RequestBody CompanyDto companyDto) {
        return (new EditCompanyAction()).doActionOnCompany(companyRepository, mongoTemplate, authorization,
                correlationToken, companyDto);
    }

    @RequestMapping(value = "/api/v1/user-store/company/{companyId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get company by companyId.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Company found", response = CompanyDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Company not found", response = UserDto.class) })
    public ResponseEntity<CompanyDto> getCompany(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken,
            @PathVariable("companyId") String companyId) {
        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("companyId", companyId);
        return (new GetCompanyAction()).doActionOnCompany(companyRepository, mongoTemplate, authorization,
                correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/user-store/company/tenant/{companyId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get tenantid by companyId.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Company found", response = String.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Company not found", response = String.class) })
    public ResponseEntity<String> getTenantIdById(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken,
            @PathVariable("companyId") String companyId) {

        return (new GetTenantIdByIdAction()).doAction(companyRepository, authorization, correlationToken,
                companyId);
    }

    @RequestMapping(value = "/api/v1/user-store/company/list/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get company by userId.", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Company found", response = CompanyDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Company not found", response = CompanyDto.class) })
    public ResponseEntity<List<CompanyDto>> getCompanyByUserId(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable("userId") String userId) {
        return (new GetCompanyListByUserId()).doAction(mongoTemplate, authorization, correlationToken, userId);
    }
}
