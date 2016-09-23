package s3f.ka_user_store.actions.company;

import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import s3f.ka_user_store.dtos.CompanyDto;
import s3f.ka_user_store.interfaces.CompanyRepository;
import s3f.ka_user_store.logging.LoggerHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSBurger on 19.09.2016.
 */
public class GetUserList implements CompanyActions<String> {


    @Autowired
    private CompanyRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ResponseEntity<List<String>> doActionOnCompany(CompanyRepository companyRepository,
                                               MongoTemplate mongoTemplate,
                                               String authorization,
                                               String correlationToken,
                                               String httpValues) {
        LoggerHelper.logData(Level.INFO,"Get user list of a comopany",correlationToken,authorization, GetUserList.class.getName());
        CompanyDto companyDtoTemp = companyRepository.findOneByCompanyId(httpValues);
        if (companyDtoTemp == null) {
            LoggerHelper.logData(Level.INFO,"Company not found",correlationToken,authorization, GetUserList.class.getName());
            return new ResponseEntity<List<String>>(new ArrayList<String>(), HttpStatus.NOT_FOUND);
        }

        if(companyDtoTemp.getAssignedUserId().isEmpty()){
            LoggerHelper.logData(Level.INFO,"user list is empty.",correlationToken,authorization, GetUserList.class.getName());
            return new ResponseEntity<List<String>>(new ArrayList<String>(), HttpStatus.NO_CONTENT);
        }

        List<String> assignedUserList = new ArrayList<String>();
        assignedUserList.addAll(companyDtoTemp.getAssignedUserId());
        LoggerHelper.logData(Level.INFO,"user list return.",correlationToken,authorization, GetUserList.class.getName());
        return new ResponseEntity<List<String>>(assignedUserList, HttpStatus.OK);
    }
}
