package hu.zalatnai.discountwarehouse.users;

import com.github.restdriver.clientdriver.ClientDriverRule;
import hu.zalatnai.discountwarehouse.DiscountWarehouseApplication;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringApplicationConfiguration(classes = DiscountWarehouseApplication.class)
@IntegrationTest
public class UserExistenceCheckerIntegrationTest {
    private static final String username1 = "Tom34";
    private static final String username2 = "Tom35";
    private static final String nonExistentUsername = "dfgfd";

    @Autowired
    private UserExistenceChecker userExistenceChecker;

    @Rule
    public ClientDriverRule api = new ClientDriverRule(6000);

    @Test(expected = UserNotFoundException.class)
    public void throwsIfTheApiReturnsThatNoUserByTheGivenUsernameExist() {
        api.addExpectation(
            onRequestTo("/api/users/" + nonExistentUsername),
            giveResponse("{}", "application/json")
        ).times(1);
        this.userExistenceChecker.checkIfUserExists(nonExistentUsername);
    }

    @Test
    public void returnsIfTheApiReturnsThatAUserByTheGivenUsernameIndeedExists() {
        api.addExpectation(
            onRequestTo("/api/users/" + username1),
            giveResponse("{\"x\": \"y\"}", "application/json")
        ).times(1);
        this.userExistenceChecker.checkIfUserExists(username1);
    }

    @Test
    public void consultsTheApiOnlyOnceRegardingTheExistenceOfTheUserThenItUsesTheCachedResponse() {
        api.addExpectation(
            onRequestTo("/api/users/" + username2),
            giveResponse("{\"x\": \"y\"}", "application/json")
        ).times(1);
        this.userExistenceChecker.checkIfUserExists(username2);
        this.userExistenceChecker.checkIfUserExists(username2);
    }
}