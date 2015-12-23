package hu.zalatnai.discountwarehouse.products.infrastructure;

import hu.zalatnai.discountwarehouse.products.*;
import hu.zalatnai.discountwarehouse.sdk.CachingListenableFutureProviderDecorator;
import hu.zalatnai.discountwarehouse.sdk.ListenableFutureProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProductsInfrastructureConfiguration {
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductsConfiguration productsConfiguration;

    @Autowired
    private PurchasesConfiguration purchasesConfiguration;

    @Bean
    public ListenableFutureProvider<Integer, ResponseEntity<PurchaseContainer>> purchaseContainerProvider() {
        return new CachingListenableFutureProviderDecorator<>(
            new PurchasesByProductIdFutureProvider(asyncRestTemplate, purchasesConfiguration)
        );
    }

    @Bean
    public ListenableFutureProvider<Integer, ResponseEntity<Product>> productProvider() {
        return new CachingListenableFutureProviderDecorator<>(
            new ProductListenableFutureProvider(asyncRestTemplate, productsConfiguration)
        );
    }

    @Bean
    public PurchaseRepository purchaseRepository() {
        return new CachingPurchaseRepositoryDecorator(new RESTPurchaseRepository(restTemplate, purchasesConfiguration));
    }
}
