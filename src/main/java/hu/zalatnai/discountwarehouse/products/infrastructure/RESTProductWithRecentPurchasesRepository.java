package hu.zalatnai.discountwarehouse.products.infrastructure;

import java.util.*;
import java.util.concurrent.ExecutionException;

import com.google.common.base.Throwables;
import hu.zalatnai.discountwarehouse.products.*;
import hu.zalatnai.discountwarehouse.sdk.ListenableFutureProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
class RESTProductWithRecentPurchasesRepository implements ProductWithRecentPurchasesRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ListenableFutureProvider<Integer, ResponseEntity<PurchaseContainer>> purchaseListenableFutureProvider;
    @Autowired
    ListenableFutureProvider<Integer, ResponseEntity<Product>> productListenableFutureProvider;

    @Override
    public List<ProductWithRecentPurchases> getByIds(Collection<Integer> productIds) {
        List<ListenableFuture<ResponseEntity<PurchaseContainer>>> purchaseContainerFutures = new ArrayList<>();
        List<ListenableFuture<ResponseEntity<Product>>> productFutures = new ArrayList<>();
        List<ProductWithRecentPurchases> productWithRecentPurchasesList = new ArrayList<>();

        for (int productId : productIds) {
            purchaseContainerFutures.add(purchaseListenableFutureProvider.get(productId));
            productFutures.add(productListenableFutureProvider.get(productId));
        }

        //processing time here is (max(future1.time, ..., futureN.time) + object instantiation/processing time)
        for (int i = 0; i < productIds.size(); i++) {
            try {
                Product product = productFutures.get(i).get().getBody();
                PurchaseContainer purchaseContainer = purchaseContainerFutures.get(i).get().getBody();

                Set<String> recentBuyers = new HashSet<>();
                for (Purchase purchase : purchaseContainer.getPurchases()) {
                    recentBuyers.add(purchase.getUsername());
                }

                productWithRecentPurchasesList.add(
                    new ProductWithRecentPurchases(product.getId(), product.getFace(), product.getPrice(),
                        product.getSize(), recentBuyers, purchaseContainer.getPurchases().length
                    )
                );
            } catch (ExecutionException e) {
                Throwables.propagate(e);
            } catch (InterruptedException e) {
                log.error("InterruptedException", e);
            }
        }

        return productWithRecentPurchasesList;
    }
}
