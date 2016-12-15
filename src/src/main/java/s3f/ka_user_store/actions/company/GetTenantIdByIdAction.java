package s3f.ka_user_store.actions.company;

import org.apache.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import s3f.framework.logger.LoggerHelper;
import s3f.ka_user_store.dtos.CompanyDto;
import s3f.ka_user_store.interfaces.CompanyRepository;

public class GetTenantIdByIdAction {

    public ResponseEntity<String> doAction(CompanyRepository companyRepository, String authorization,
            String correlationToken, String companyId) {
        CompanyDto companyDtoTemp = companyRepository.findOneByCompanyId(companyId);
        if (companyDtoTemp == null) {
            LoggerHelper.logData(Level.INFO, "Company not found", correlationToken, authorization,
                    GetTenantIdByIdAction.class.getName());
            return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(companyDtoTemp.getTenant_uuid(), HttpStatus.OK);
    }
}
