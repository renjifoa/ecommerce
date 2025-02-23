package com.onebox.ecommerce.model;

import lombok.Getter;

@Getter
public class Product {
    private final Long id;
    private final String description;
    private int amount;

    public Product(Long id, String description, int amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
    }
}
