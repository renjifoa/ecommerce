package com.onebox.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cart {
    private final String id;
    private final List<Product> products;
    private LocalDateTime  lastUpdated;

    public Cart(String id) {
        this.id = id;
        this.products = new ArrayList<>();
        this.lastUpdated = LocalDateTime.now();
    }


    public void addProduct(Product product) {
        products.add(product);
        updateTimestamp();
    }


    public void deleteProduct(Product product) {
        products.remove(product);
        updateTimestamp();
    }

    public void updateTimestamp() {
        this.lastUpdated = LocalDateTime.now();
    }
}
