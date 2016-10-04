package s3f.ka_user_store.actions.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import s3f.framework.logger.LoggerHelper;
import s3f.framework.rest.RestCallBuilder;
import s3f.framework.rest.Interfaces.RestCallGet;
import s3f.ka_user_store.dtos.CompanyDto;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.CompanyRepository;

/**
 * Created by MSBurger on 19.09.2016.
 */
public class GetUserList implements CompanyActions<Map<String,String>> {


    @Autowired
    private CompanyRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ResponseEntity<List<UserDto>> doActionOnCompany(CompanyRepository companyRepository,
                                               MongoTemplate mongoTemplate,
                                               String authorization,
                                               String correlationToken,
                                               Map<String,String> httpValues) {
        LoggerHelper.logData(Level.INFO,"Get user list of a comopany",correlationToken,authorization, GetUserList.class.getName());
        CompanyDto companyDtoTemp = companyRepository.findOneByCompanyId(httpValues.get("companyId"));
        if (companyDtoTemp == null) {
            LoggerHelper.logData(Level.INFO,"Company not found",correlationToken,authorization, GetUserList.class.getName());
            return new ResponseEntity<List<UserDto>>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }

        if(companyDtoTemp.getAssignedUserId().isEmpty()){
            LoggerHelper.logData(Level.INFO,"user list is empty.",correlationToken,authorization, GetUserList.class.getName());
            return new ResponseEntity<List<UserDto>>(new ArrayList<>(), HttpStatus.NO_CONTENT);
        }
        
        List<UserDto> result = extract(authorization, correlationToken, companyDtoTemp.getAssignedUserId(),httpValues.get("port"));
        LoggerHelper.logData(Level.INFO,"user list return.",correlationToken,authorization, GetUserList.class.getName());
        return new ResponseEntity<List<UserDto>>(result, HttpStatus.OK);
    }

	private List<UserDto> extract(String authorization, String correlationToken, List<String> assignedUserList,String port) {
		List<UserDto> users = new ArrayList<>();
		Map<String,String> headers=new HashMap<>();
        headers.put("Authorization",authorization);
        headers.put("CorrelationToken", correlationToken);
        Map<String,String> uriVariables=new HashMap<>();
        for(String userId : assignedUserList){
        	uriVariables.put("userId", userId);
        	RestCallGet<UserDto> call=new RestCallBuilder().buildGet("http://localhost:"+port+"/", "v1", "/api/v1/user-store/user/{userId}/", headers, uriVariables, UserDto.class);
        	ResponseEntity<UserDto> userFound = call.execute();
        	users.add(userFound.getBody());
        }
        return  users;
	}
}
