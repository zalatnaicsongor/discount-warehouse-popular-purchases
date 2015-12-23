package hu.zalatnai.discountwarehouse.products.infrastructure;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import hu.zalatnai.discountwarehouse.products.Purchase;
import hu.zalatnai.discountwarehouse.products.PurchaseContainer;
import hu.zalatnai.discountwarehouse.products.PurchaseRepository;
import hu.zalatnai.discountwarehouse.products.PurchasesConfiguration;

import org.springframework.web.client.RestTemplate;

class RESTPurchaseRepository implements PurchaseRepository {
    private final RestTemplate restTemplate;

    private final PurchasesConfiguration purchasesConfiguration;

    RESTPurchaseRepository(RestTemplate restTemplate, PurchasesConfiguration purchasesConfiguration) {
        this.restTemplate = restTemplate;
        this.purchasesConfiguration = purchasesConfiguration;
    }

    @Override
    public Collection<Purchase> getFiveMostRecentByUsername(String username) {
        Map<String, Integer> limitMap = new HashMap<>();
        limitMap.put("limit", 5);
        return Arrays.asList(this.restTemplate.getForObject(
            purchasesConfiguration.getBaseUrl() +
            "/" +
            purchasesConfiguration.getByUserPath() +
            "/" +
            username +
            "?limit={limit}",
            PurchaseContainer.class,
            limitMap
        ).getPurchases());
    }

}