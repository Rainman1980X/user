package s3f.ka_user_store.controllers;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import s3f.framework.deserialization.S3FDeseserializer;
import s3f.framework.lifecycle.LifeCycle;
import s3f.framework.logger.LoggerHelper;
import s3f.framework.messaging.amqp.dto.RabbitMQContainer;
import s3f.framework.messaging.amqp.job.configuration.JobChannelConfiguration;
import s3f.framework.rest.DirectRestCallBuilder;
import s3f.framework.serialization.S3FSerializer;
import s3f.ka_user_store.events.CreateLDAPUserEventHandler;

@Service
public class UserMessagesController {

    @Value("${datev.registration.domainRouting}")
    private String domainRouting;

    @Value("${datev.servicegateway.user}")
    private String user;

    @Value("${datev.servicegateway.password}")
    private String password;

    @Value("${datev.servicegateway.host}")
    private String serviceGatewayHost;

    @Autowired
    private DirectRestCallBuilder restCallBuilder;

    private RabbitMQContainer jobContainer;

    private S3FDeseserializer s3fDeseserializer;

    private S3FSerializer s3fSerializer;

    @Autowired
    public UserMessagesController(JobChannelConfiguration jobChannelConfiguration,
            S3FDeseserializer s3fDeseserializer,
            S3FSerializer s3fSerializer, LifeCycle lifeCycle)
            throws RuntimeException {
        this.s3fDeseserializer = s3fDeseserializer;
        this.s3fSerializer = s3fSerializer;
        try {
            jobContainer = jobChannelConfiguration.build(lifeCycle.getKey());
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR, "Can't build RabbitMQ job container", "LDAPuser", "LDAPuserAuth",
                    UserMessagesController.class.getName(), e);
            throw new RuntimeException("Can't build RabbitMQ container", e);
        }
    }

    @PostConstruct
    public void bindJenkinsNewDeploymentEvent() throws IOException {
        CreateLDAPUserEventHandler createLDAPUserEventHandler = new CreateLDAPUserEventHandler(restCallBuilder,
                jobContainer,
                domainRouting,
                s3fDeseserializer, s3fSerializer, user, password, serviceGatewayHost);

        jobContainer.getChannel().queueBind(jobContainer.getQueue(), jobContainer.getExchange(), domainRouting + ".*");
        jobContainer.getChannel().basicConsume(jobContainer.getQueue(), false, domainRouting + ".*",
                createLDAPUserEventHandler);
    }
}
