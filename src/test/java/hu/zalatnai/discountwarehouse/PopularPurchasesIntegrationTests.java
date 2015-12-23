package hu.zalatnai.discountwarehouse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DiscountWarehouseApplication.class)
@IntegrationTest
public class PopularPurchasesIntegrationTests {

	@Test
	public void contextLoads() {
	}

}
