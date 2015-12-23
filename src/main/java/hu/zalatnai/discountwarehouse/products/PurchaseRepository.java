package hu.zalatnai.discountwarehouse.products;

import java.util.Collection;

public interface PurchaseRepository {
    /**
     * Gets the five most recent purchases of a user.
     * Yes, five is hardcoded. (I could've added another parameter, but this is just a test task)
     * @param username username of the user
     * @return A collection of the five most recent purchases of the user
     */
    Collection<Purchase> getFiveMostRecentByUsername(String username);
}
