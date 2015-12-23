package hu.zalatnai.discountwarehouse.products;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductWithRecentPurchases extends Product {
    @JsonProperty
    private Set<String> recent;

    @JsonIgnore
    private int numOfRecentPurchases;

    public ProductWithRecentPurchases(int id, String face, int price, int size, Set<String> recent, int numOfRecentPurchases) {
        super(id, face, price, size);
        this.recent = recent;
        this.numOfRecentPurchases = numOfRecentPurchases;
    }

    public Set<String> getRecent() {
        return recent;
    }

    public int getNumOfRecentPurchases() {
        return numOfRecentPurchases;
    }
}
