package hu.zalatnai.discountwarehouse;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import hu.zalatnai.discountwarehouse.popularpurchases.PopularPurchasesController;
import hu.zalatnai.discountwarehouse.popularpurchases.PopularPurchasesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DiscountWarehouseApplication.class)
@IntegrationTest
public class PopularPurchasesAcceptanceTests {
    @Autowired
    private PopularPurchasesService popularPurchasesService;

    public static final String nonExistentUsername = "non_existent_username_qwe";
    public static final String existingUsername = "Tom34";

    @Before
    public void configureRestAssured() {
        RestAssuredMockMvc.standaloneSetup(new PopularPurchasesController(popularPurchasesService));
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
            assertThat().content(equalTo("User with username of '" + nonExistentUsername + "' was not found")).
            statusCode(404);
    }

    @Test
    public void returns200IfAnExistingUsernameIsSupplied() {
        when().
            get("/api/recent_purchases/" + existingUsername).
        then().
            statusCode(200);
    }

    @Test
    public void returnsTheMostRecentPurchasesOfTheUserOrderedByTheNumberOfRecentPurchasesDescending() {
        when().
            get("/api/recent_purchases/" + existingUsername).
        then().
            body("id", contains(485837, 855330, 188412, 83777, 238223));
    }
    //@formatter:on
}
