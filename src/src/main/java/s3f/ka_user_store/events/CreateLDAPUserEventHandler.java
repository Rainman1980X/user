package s3f.ka_user_store.events;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import s3f.Application;
import s3f.framework.deserialization.S3FDeseserializer;
import s3f.framework.events.S3FEvent;
import s3f.framework.logger.LoggerHelper;
import s3f.framework.messaging.amqp.dto.RabbitMQContainer;
import s3f.framework.messaging.amqp.error.ErrorMessageHandler;
import s3f.framework.rest.DirectRestCallBuilder;
import s3f.framework.rest.interfaces.RestCallPost;
import s3f.framework.serialization.S3FSerializer;
import s3f.ka_user_store.actions.user.CreateUserAction;
import s3f.ka_user_store.dtos.MappingConverter;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.dtos.UserLDAPDto;
import s3f.ka_user_store.interfaces.UserRepository;
import s3f.ka_user_store.services.LDAPUserService;

public class CreateLDAPUserEventHandler extends DefaultConsumer {

    private final LDAPUserService ldapUserService;
    private final ErrorMessageHandler errorMessageHandler;
    private SecureRandom random = new SecureRandom();
    private String domainRouting;
    private DirectRestCallBuilder restCallBuilder;
    private RabbitMQContainer container;
    private Map<String,String> userData;
    //private UserDto userDto;
    private S3FDeseserializer s3fDeseserializer;
    private S3FSerializer s3fSerializer;
    private String user;
    private String password;
    private String serviceGatewayHost;
    private UserRepository userRepository;

    public CreateLDAPUserEventHandler(RabbitMQContainer container,
                                      String domainRouting, S3FDeseserializer s3fDeseserializer, S3FSerializer s3fSerializer, UserRepository userRepository, LDAPUserService ldapUserService, ErrorMessageHandler errorMessageHandler) {
        super(container.getChannel());
        this.container = container;
        this.domainRouting = domainRouting;
        this.s3fDeseserializer = s3fDeseserializer;
        this.s3fSerializer = s3fSerializer;
        this.userRepository = userRepository;
        this.ldapUserService = ldapUserService;
        this.errorMessageHandler = errorMessageHandler;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
            throws IOException {

        if (envelope.getRoutingKey().equals(domainRouting + ".createUser")) {
            long deliveryTag = envelope.getDeliveryTag();
            S3FEvent event = null;
            try {
                event = s3fDeseserializer.deserialize(new String(body),S3FEvent.class);
                userData = (Map<String,String>)event.getData();
                UserLDAPDto userLDAPDto = ldapUserService.createLDAPUser(buildUserLDAPDtoFromMap(userData));
                userData.put("userId",userLDAPDto.getAccount_uuid());
                (new CreateUserAction()).doActionOnUser(userRepository, null, event.getAuthorization(), event.getCorrelationId(), buildUserFromMap(userData));
            } catch (Exception e) {
                LoggerHelper.logData(Level.ERROR, "Handle Event fails", "Rabbit", "RabbitAuth",
                        this.getClass().getName(), e);
                errorMessageHandler.publishErrorMessage(Application.serviceName,Application.version,e,userData,event,envelope);
                container.getChannel().basicReject(envelope.getDeliveryTag(),false);
            } finally {
                container.getChannel().basicAck(deliveryTag, false);
            }
        } else {

            container.getChannel().basicReject(envelope.getDeliveryTag(), true);
        }
    }

    private S3FEvent eventParser(byte[] body) throws IOException {
        S3FEvent event = s3fDeseserializer.deserialize(new String(body, Charset.defaultCharset()), S3FEvent.class);
        userData = s3fDeseserializer.deserializeAsMap(s3fSerializer.toJson(event.getData()));
        LoggerHelper.logData(Level.DEBUG, "Parse body " + userData.toString(), "Rabbit", "RabbitAuth",
                this.getClass().getName());
        return event;
    }

    private UserLDAPDto buildUserLDAPDtoFromMap(Map<String,String> userData){
        UserLDAPDto userLDAPDto = new UserLDAPDto();
        userLDAPDto.setEmail(userData.get("email"));
        userLDAPDto.setGiven_name(userData.get("givenname"));
        userLDAPDto.setFamily_name(userData.get("surename"));
        userLDAPDto.setPassword(userData.get("password"));
        userLDAPDto.setDisplay_name(userLDAPDto.getFamily_name()+", "+userLDAPDto.getGiven_name());
        return userLDAPDto;
    }

    private UserDto buildUserFromMap(Map<String,String> userData){
        UserDto dto =  new UserDto();
        dto.setUserId(userData.get("userId"));
        dto.setGivenname(userData.get("givenname"));
        dto.setSurename(userData.get("surename"));
        dto.setEmail(userData.get("email"));
        return dto;
    }

    private String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }
}