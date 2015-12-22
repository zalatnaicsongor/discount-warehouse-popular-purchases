package hu.zalatnai.discountwarehouse.products;

import java.util.Collection;

public interface PurchaseRepository {
    Collection<Purchase> getNMostRecentByUsername(String username, int limit);
}
