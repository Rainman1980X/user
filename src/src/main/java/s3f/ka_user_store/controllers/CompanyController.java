package s3f.ka_user_store.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RestController;
import s3f.ka_user_store.interfaces.UserRepository;

/**
 * Created by MSBurger on 09.09.2016.
 */
@RestController
public class CompanyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
}
