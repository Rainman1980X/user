package s3f.ka_user_store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.framework.rest.DirectRestCallBuilder;
import s3f.framework.rest.RestCallBuilder;
import s3f.ka_user_store.actions.company.CreateCompanyAction;
import s3f.ka_user_store.actions.company.CreateCompanyLDAPAction;
import s3f.ka_user_store.dtos.CompanyDto;
import s3f.ka_user_store.dtos.CompanyLDAPDto;
import s3f.ka_user_store.interfaces.CompanyRepository;

@Service
public class CreateCompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RestCallBuilder restCallBuilder;

    @Autowired
    private DirectRestCallBuilder directRestCallBuilder;

    @Value("${datev.servicegateway.user}")
    private String user;

    @Value("${datev.servicegateway.password}")
    private String password;

    @Value("${datev.servicegateway.host}")
    private String serviceGatewayHost;

    private String authorization;

    private String correlationToken;

    private CompanyDto companyDto;

    public CreateCompanyController() {
        // Empty default constructor
    }

    private ResponseEntity<CompanyLDAPDto> createLDAPCompany() {
        return new CreateCompanyLDAPAction().doAction(restCallBuilder, directRestCallBuilder, authorization,
                correlationToken, user, password, serviceGatewayHost);

    }

    private ResponseEntity<CompanyDto> createMonoDBCompany() {
        return new CreateCompanyAction().doAction(companyRepository, authorization, correlationToken, companyDto);
    }

    public ResponseEntity<CompanyDto> createCompany(String authorization, String correlationToken,
            CompanyDto companyDto) {
        this.authorization = authorization;
        this.correlationToken = correlationToken;
        this.companyDto = companyDto;
        return createMonoDBCompany();
    }

}
