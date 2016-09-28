package s3f.ka_user_store.controllers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import s3f.ka_user_store.dtos.UserDto;
import s3f.ka_user_store.interfaces.UserRepository;



/**
 * Created by MSBurger on 09.09.2016.
 */
//@RunWith(SpringRunner.class)
public class UserControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerTest.class);

    private UserController userController;
    private UserRepository userRepository;
    private final String authorization = "Authorization";
    private final String correlationToken = "CorrelationToken";
    private final List<String> roles = Arrays.asList(new String[]{"Admin", "contact"});
    private final UserDto userDto = new UserDto(
            "a1",
            "",
            "Herr",
            "Matthias",
            "Burger",
            "otto",
            "matthias.burger@sintec.de",
            "09287 / 956 23-200",
            new Date(),
            roles, true);

    @Before
    public void setUp() throws Exception {
        userController = new UserController();
        userRepository = mock(UserRepository.class);
        ReflectionTestUtils.setField(userController, "userRepository", userRepository);
    }

    @Test
    public void createLogin() throws Exception {
        ResponseEntity<UserDto> return0 = userController.create(authorization, authorization, userDto);
        assertTrue(return0.getStatusCodeValue() == HttpStatus.OK.value());
    }

    @Test
    public void checkDoubleLogin() {
        fail("Create Test here, embedded mongodb ");
    }

    @Test
    public void changePassword() {
        fail("Create method");
    }

    @Test
    public void changeRoles() {
        fail("Create method. Change the tests of the defined behaviours, user roles are not finally defined.");
    }
    @Test
    public void changeEmptyRoles() {
        fail("Create method. Change the tests of the defined behaviours, user roles are not finally defined.");
    }

    @Test
    public void changeUserStatusTrue() {
        fail("Create method");
    }

    @Test
    public void changeUserStatusFalse() {
        fail("Create method");
    }

    @Test
    public void changeUserValuesEmail() {
        fail("Create method");
    }

    @Test
    public void getUser() {fail("Create method"); }

    @Test
    public void getUserList() {fail("Create method"); }

    @Test
    public void getRolesListOfUser() {fail("Create method"); }

    @Test
    public void getUserStatus() {fail("Create method"); }

}