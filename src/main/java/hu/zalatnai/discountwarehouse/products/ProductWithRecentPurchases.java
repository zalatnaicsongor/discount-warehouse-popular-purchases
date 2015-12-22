package hu.zalatnai.discountwarehouse.products;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductWithRecentPurchases extends Product {
    @JsonProperty
    private String[] recent;

    public ProductWithRecentPurchases(int id, String face, int price, int size, String[] recent) {
        super(id, face, price, size);
        this.recent = recent;
    }

    public String[] getRecent() {
        return recent;
    }
}
