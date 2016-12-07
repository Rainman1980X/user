package s3f.ka_user_store.actions.company;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriUtils;

import s3f.framework.logger.LoggerHelper;
import s3f.framework.rest.DirectRestCallBuilder;
import s3f.framework.rest.RestCallBuilder;
import s3f.framework.rest.interfaces.RestCallPost;
import s3f.ka_user_store.dtos.CompanyLDAPDto;

public class CreateCompanyLDAPAction {

    private RestCallBuilder restCallBuilder;
    private DirectRestCallBuilder directRestCallBuilder;
    private String authorization;
    private String correlationToken;
    private String user;
    private String password;
    private String serviceGatewayHost;

    public ResponseEntity<CompanyLDAPDto> doAction(RestCallBuilder restCallBuilder,
            DirectRestCallBuilder directRestCallBuilder, String authorization, String correlationToken, String user,
            String password, String serviceGatewayHost) {
        this.restCallBuilder = restCallBuilder;
        this.directRestCallBuilder = directRestCallBuilder;

        this.authorization = authorization;
        this.correlationToken = correlationToken;
        this.user = user;
        this.password = password;
        this.serviceGatewayHost = serviceGatewayHost;

        LoggerHelper.logData(Level.INFO, "Create LDAP company", correlationToken, authorization,
                CreateCompanyLDAPAction.class.getName());
        return null;

    }

    private void restCalls() throws UnsupportedEncodingException {

        Map<String, String> headers = new HashMap<>();
        headers.put("correlationToken", this.correlationToken);
        headers.put("Content-Type", "application/json");
        headers.put("authorization", "Basic " + UriUtils.encode(user + ":" + password, "UTF-8"));

        Map<String, String> uri = new HashMap<>();

        RestCallPost<CompanyLDAPDto, CompanyLDAPDto> restCallPost = restCallBuilder.buildPost(serviceGatewayHost, null,
                "/iam-integration/v1/tenants", uri, headers, CompanyLDAPDto.class,
                new CompanyLDAPDto());
        ResponseEntity<CompanyLDAPDto> response = restCallPost.execute();
    }
}
