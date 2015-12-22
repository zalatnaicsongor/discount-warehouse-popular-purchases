package hu.zalatnai.discountwarehouse.users;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RESTUserExistenceCheckerTest {

    private static final String existingUsername = "existingUsername";
    private static final String nonexistentUsername = "nonexistentUsername";

    private RESTUserExistenceChecker.User existingUser = new RESTUserExistenceChecker.User("a@b.hu", existingUsername);
    private RESTUserExistenceChecker.User nonexistentUser = new RESTUserExistenceChecker.User(null, null);

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UsersConfiguration usersConfiguration;

    @InjectMocks
    private RESTUserExistenceChecker restUserExistenceChecker;

    @Before
    public void setUp() throws Exception {
        when(usersConfiguration.getBaseUrl()).thenReturn("baseUrl");
        when(restTemplate.getForObject(eq("baseUrl/" + existingUsername), any()))
            .thenReturn(existingUser);
        when(restTemplate.getForObject(eq("baseUrl/" + nonexistentUsername), any()))
            .thenReturn(nonexistentUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void throwsIfAUserWithTheSuppliedUsernameCouldNotBeFound() {
        restUserExistenceChecker.checkIfUserExists(nonexistentUsername);
    }

    @Test()
    public void returnIfAUserWithTheSuppliedUsernameCouldBeFound() {
        restUserExistenceChecker.checkIfUserExists(existingUsername);
    }
}
