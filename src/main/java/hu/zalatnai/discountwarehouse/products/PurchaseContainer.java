package hu.zalatnai.discountwarehouse.products;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PurchaseContainer {
    @JsonProperty
    private Purchase[] purchases;

    public Purchase[] getPurchases() {
        return purchases;
    }
}
