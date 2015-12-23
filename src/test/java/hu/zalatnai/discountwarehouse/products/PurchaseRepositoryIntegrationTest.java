package hu.zalatnai.discountwarehouse.products;

import com.github.restdriver.clientdriver.ClientDriverRule;
import hu.zalatnai.discountwarehouse.DiscountWarehouseApplication;
import org.junit.Before;
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
public class PurchaseRepositoryIntegrationTest {
    private static final String username = "Tom34";

    @Rule
    public ClientDriverRule api = new ClientDriverRule(6000);

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Before
    public void init() {
        api.addExpectation(
            onRequestTo("/api/purchases/by_user/" + username).withParam("limit", "5"),
            giveResponse(
                "{\"purchases\":[{\"id\":227140,\"username\":\"Tom34\",\"productId\":855330,\"date\":\"2015-12-15T01:45:16.607Z\"},{\"id\":590041,\"username\":\"Tom34\",\"productId\":188412,\"date\":\"2015-12-05T20:51:51.607Z\"},{\"id\":96207,\"username\":\"Tom34\",\"productId\":485837,\"date\":\"2015-12-14T04:09:37.607Z\"},{\"id\":718469,\"username\":\"Tom34\",\"productId\":238223,\"date\":\"2015-12-16T18:16:05.607Z\"},{\"id\":795055,\"username\":\"Tom34\",\"productId\":83777,\"date\":\"2015-12-05T22:33:12.607Z\"}]}",
                "application/json"
            )
        ).times(1);
    }

    @Test
    @DirtiesContext
    public void retrievesTheFiveMostRecentPurchasesByUsernameFromTheApi() {
        purchaseRepository.getFiveMostRecentByUsername(username);
    }

    @Test
    public void onlyCallsTheFirstTimeThenItServesTheResponseFromCache() {
        purchaseRepository.getFiveMostRecentByUsername(username);
        purchaseRepository.getFiveMostRecentByUsername(username);
    }

}