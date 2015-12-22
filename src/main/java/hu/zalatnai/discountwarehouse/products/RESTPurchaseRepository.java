package hu.zalatnai.discountwarehouse.products;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class RESTPurchaseRepository implements PurchaseRepository {
    private final RestTemplate restTemplate;

    private final PurchasesConfiguration purchasesConfiguration;

    @Autowired
    RESTPurchaseRepository(RestTemplate restTemplate, PurchasesConfiguration purchasesConfiguration) {
        this.restTemplate = restTemplate;
        this.purchasesConfiguration = purchasesConfiguration;
    }

    @Override
    public Collection<Purchase> getNMostRecentByUsername(String username, int limit) {
        return this.restTemplate.getForObject(
            purchasesConfiguration.getBaseUrl() +
            "/" +
            purchasesConfiguration.getByUserPath() +
            "/" +
            username +
            "?limit" +
            limit,
            PurchaseContainer.class
        ).getPurchases();
    }

    class PurchaseContainer {
        @JsonProperty
        private List<Purchase> purchases;

        public List<Purchase> getPurchases() {
            return purchases;
        }
    }
}
