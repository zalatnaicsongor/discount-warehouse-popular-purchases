package hu.zalatnai.discountwarehouse.products;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import hu.zalatnai.discountwarehouse.sdk.ListenableFutureProvider;

import org.springframework.stereotype.Service;

@Service
class RESTProductWithRecentPurchasesRepository implements ProductWithRecentPurchasesRepository {
    ListenableFutureProvider<String, List<Purchase>> purchaseListenableFutureProvider;
    ListenableFutureProvider<String, Product> productListenableFutureProvider;

    @Override public List<ProductWithRecentPurchases> getByIds(Collection<Integer> productIds) {
        return Collections.emptyList();
    }
}
