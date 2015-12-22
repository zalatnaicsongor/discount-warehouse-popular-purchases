package hu.zalatnai.discountwarehouse.products;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Purchase {
    private final int id;
    private final int productId;
    private final String username;
    private final LocalDate date;

    @JsonCreator
    public Purchase(
        @JsonProperty("id") int id,
        @JsonProperty("product_id") int productId,
        @JsonProperty("username") String username,
        @JsonProperty("date") String date
    ) {
        this.id = id;
        this.productId = productId;
        this.username = username;
        this.date = LocalDate.parse(date);
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Purchase purchase = (Purchase) o;
        return id == purchase.id;
    }

    @Override public int hashCode() {
        return Objects.hash(id);
    }
}
