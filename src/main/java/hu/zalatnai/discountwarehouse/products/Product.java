package hu.zalatnai.discountwarehouse.products;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

class Product {
    @JsonProperty
    private int id;

    @JsonProperty
    private String face;

    @JsonProperty
    private int price;

    @JsonProperty
    private int size;


    public Product(int id, String face, int price, int size) {
        this.id = id;
        this.face = face;
        this.price = price;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public String getFace() {
        return face;
    }

    public int getPrice() {
        return price;
    }

    public int getSize() {
        return size;
    }
}
