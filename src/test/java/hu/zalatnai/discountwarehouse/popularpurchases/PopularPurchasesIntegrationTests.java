package hu.zalatnai.discountwarehouse.popularpurchases;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PopularPurchasesApplication.class)
@IntegrationTest
public class PopularPurchasesIntegrationTests {

	@Test
	public void contextLoads() {
	}

}
