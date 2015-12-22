package hu.zalatnai.discountwarehouse.products;

import hu.zalatnai.discountwarehouse.sdk.ListenableFutureProvider;

import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
class PurchasesByProductIdProvider implements ListenableFutureProvider<String, Purchase> {
    @Override public ListenableFuture<Purchase> get(String source) {
        return null;
    }
}
