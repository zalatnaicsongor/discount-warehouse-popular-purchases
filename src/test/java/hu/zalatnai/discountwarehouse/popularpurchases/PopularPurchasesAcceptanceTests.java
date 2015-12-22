package hu.zalatnai.discountwarehouse.popularpurchases;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import hu.zalatnai.discountwarehouse.popularpurchases.purchases.RecentPurchasesController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static com.jayway.restassured.module.mockmvc.matcher.RestAssuredMockMvcMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PopularPurchasesApplication.class)
@IntegrationTest
public class PopularPurchasesAcceptanceTests {
    public static String nonExistentUsername = "non_existent_username_qwe";

    @Before public void configureRestAssured() {
        RestAssuredMockMvc.standaloneSetup(new RecentPurchasesController());
    }

    //@formatter:off
    @Test
    public void returns404IfAUsernameIsNotSupplied() {
        when().
            get("/api/recent_purchases/").
        then().
            statusCode(404);
    }

    @Test
    public void returns404IfANonExistentUsersNameIsSupplied() {
        when().
            get("/api/recent_purchases/" + nonExistentUsername).
        then().
            assertThat().content(equalTo("User with username of '"+nonExistentUsername+"' was not found")).
            statusCode(404);
    }
    //@formatter:on
}
