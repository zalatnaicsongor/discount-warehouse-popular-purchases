package hu.zalatnai.discountwarehouse.products;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.stereotype.Service;

@Service
class RESTPurchaseRepository implements PurchaseRepository {
    @Override public Collection<Purchase> getNMostRecentByUsername(String username, int limit) {
        return Collections.emptyList();
    }

    class PurchaseContainer {
        @JsonProperty
        private List<Purchase> purchases;

        public List<Purchase> getPurchases() {
            return purchases;
        }
    }
}
