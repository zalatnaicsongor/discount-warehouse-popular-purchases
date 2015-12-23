package hu.zalatnai.discountwarehouse.products;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Product {
    private int id;

    private String face;

    private int price;

    private int size;

    //https://github.com/x-team/daw-purchases#get-productsid -> please update the documentation because the said
    //endpoint returns the object wrapped in the 'product' property
    @SuppressWarnings("unchecked")
    @JsonCreator
    Product(Map<String, Object> props) {
        Map<String, Object> productProps = (Map<String, Object>) props.get("product");
        this.id = (int) productProps.get("id");
        this.face = (String) productProps.get("face");
        this.price = (int) productProps.get("price");
        this.size = (int) productProps.get("size");
    }

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
