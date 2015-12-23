package hu.zalatnai.discountwarehouse.products.infrastructure;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import hu.zalatnai.discountwarehouse.products.Purchase;
import hu.zalatnai.discountwarehouse.products.PurchaseRepository;

class CachingPurchaseRepositoryDecorator implements PurchaseRepository {
    private final PurchaseRepository purchaseRepository;

    private final LoadingCache<String, Collection<Purchase>> cache;

    //todo: inject cache, maybe add configurable parameters.
    CachingPurchaseRepositoryDecorator(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
        this.cache = Caffeine.newBuilder().maximumSize(10000).expireAfterWrite(
            10,
            TimeUnit.MINUTES
        ).build(purchaseRepository::getFiveMostRecentByUsername);
    }


    @Override
    public Collection<Purchase> getFiveMostRecentByUsername(String username) {
        return cache.get(username);
    }
}
