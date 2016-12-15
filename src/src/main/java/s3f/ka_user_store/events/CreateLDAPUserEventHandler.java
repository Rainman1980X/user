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

import s3f.framework.deserialization.S3FDeseserializer;
import s3f.framework.events.S3FEvent;
import s3f.framework.logger.LoggerHelper;
import s3f.framework.messaging.amqp.dto.RabbitMQContainer;
import s3f.framework.rest.DirectRestCallBuilder;
import s3f.framework.rest.interfaces.RestCallPost;
import s3f.framework.serialization.S3FSerializer;
import s3f.ka_user_store.actions.user.CreateUserAction;
import s3f.ka_user_store.dtos.MappingConverter;
import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.dtos.UserLDAPDto;
import s3f.ka_user_store.interfaces.UserRepository;

public class CreateLDAPUserEventHandler extends DefaultConsumer {

    private SecureRandom random = new SecureRandom();
    private String domainRouting;
    private DirectRestCallBuilder restCallBuilder;
    private RabbitMQContainer container;
    private UserDto userDto;
    private S3FDeseserializer s3fDeseserializer;
    private S3FSerializer s3fSerializer;
    private String user;
    private String password;
    private String serviceGatewayHost;
    private UserRepository userRepository;

    public CreateLDAPUserEventHandler(DirectRestCallBuilder restCallBuilder, RabbitMQContainer container,
                                      String domainRouting, S3FDeseserializer s3fDeseserializer, S3FSerializer s3fSerializer, String user,
                                      String password, String serviceGatewayHost, UserRepository userRepository) {
        super(container.getChannel());
        this.restCallBuilder = restCallBuilder;
        this.container = container;
        this.domainRouting = domainRouting;
        this.s3fDeseserializer = s3fDeseserializer;
        this.s3fSerializer = s3fSerializer;
        this.user = user;
        this.password = password;
        this.serviceGatewayHost = serviceGatewayHost;
        this.userRepository = userRepository;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
            throws IOException {

        if (envelope.getRoutingKey().equals(domainRouting + ".createUser")) {
            long deliveryTag = envelope.getDeliveryTag();
            try {
                S3FEvent event = eventParser(body);
                createLDAPEntry();
                (new CreateUserAction()).doActionOnUser(userRepository, null, event.getAuthorization(), event.getCorrelationId(), userDto);
            } catch (Exception e) {
                LoggerHelper.logData(Level.ERROR, "Handle Event fails", "Rabbit", "RabbitAuth",
                        this.getClass().getName(), e);

            } finally {
                container.getChannel().basicAck(deliveryTag, false);
            }
        } else {

            container.getChannel().basicReject(envelope.getDeliveryTag(), true);
        }
    }

    private S3FEvent eventParser(byte[] body) throws IOException {

        S3FEvent event = s3fDeseserializer.deserialize(new String(body, Charset.defaultCharset()), S3FEvent.class);
        userDto = s3fDeseserializer.deserialize(s3fSerializer.toJson(event.getData()), UserDto.class);
        LoggerHelper.logData(Level.DEBUG, "Parse body " + userDto.toString(), "Rabbit", "RabbitAuth",
                this.getClass().getName());
        return event;
    }

    private void createLDAPEntry() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization",
                "Basic " + Base64Utils.encodeToString((user + ":" + password).getBytes(Charset.defaultCharset())));
        headers.put("Request-Id", nextSessionId());
        Map<String, String> uri = new HashMap<>();

        RestCallPost<UserLDAPDto, UserLDAPDto> restCallPost = restCallBuilder.buildPost(serviceGatewayHost, null,
                "/iam-integration/v1/persons", headers, uri, UserLDAPDto.class, MappingConverter.convert(userDto));
        ResponseEntity<UserLDAPDto> responseEntity = restCallPost.execute();
        userDto.setUserId(responseEntity.getBody().getAccount_uuid());
    }

    private String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }
}