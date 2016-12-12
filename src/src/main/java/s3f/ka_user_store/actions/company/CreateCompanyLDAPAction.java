package s3f.ka_user_store.actions.company;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;

import s3f.framework.logger.LoggerHelper;
import s3f.framework.rest.DirectRestCallBuilder;
import s3f.framework.rest.interfaces.RestCallPost;
import s3f.ka_user_store.dtos.CompanyDto;
import s3f.ka_user_store.dtos.CompanyLDAPDto;
import s3f.ka_user_store.dtos.MappingConverter;

public class CreateCompanyLDAPAction {

    private DirectRestCallBuilder restCallBuilder;
    private String user;
    private String password;
    private String serviceGatewayHost;
    private CompanyDto companyDto;
    private SecureRandom random = new SecureRandom();

    public ResponseEntity<CompanyDto> doAction(DirectRestCallBuilder restCallBuilder, String authorization,
            String correlationToken, String user, String password, String serviceGatewayHost, CompanyDto companyDto) {
        this.restCallBuilder = restCallBuilder;
        this.user = user;
        this.password = password;
        this.serviceGatewayHost = serviceGatewayHost;
        try {
            this.companyDto = createLDAPCompanyEntry(companyDto);
        } catch (UnsupportedEncodingException e) {
            LoggerHelper.logData(Level.ERROR, "Create LDAP company fails", correlationToken, authorization,
                    CreateCompanyLDAPAction.class.getName(), e);
            return new ResponseEntity<CompanyDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LoggerHelper.logData(Level.INFO, "Create LDAP company", correlationToken, authorization,
                CreateCompanyLDAPAction.class.getName());
        return new ResponseEntity<CompanyDto>(this.companyDto, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private CompanyDto createLDAPCompanyEntry(CompanyDto companyDto2) throws UnsupportedEncodingException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization",
                "Basic " + Base64Utils.encodeToString((user + ":" + password).getBytes(Charset.defaultCharset())));
        headers.put("Request-Id", nextSessionId());
        Map<String, String> uri = new HashMap<>();

        RestCallPost<CompanyLDAPDto, CompanyLDAPDto> restCallPost = restCallBuilder.buildPost(serviceGatewayHost, null,
                "/iam-integration/v1/tenants", headers, uri, CompanyLDAPDto.class,
                MappingConverter.convert(companyDto2));
        ResponseEntity<CompanyLDAPDto> response = restCallPost.execute();
        return MappingConverter.convert(response.getBody());

    }

    private String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }
}
