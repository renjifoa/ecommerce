package com.onebox.ecommerce.model;

import lombok.Getter;

@Getter
public class Product {
    private final Long id;
    private final String description;
    private final double amount;

    public Product(Long id, String description, double amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
    }
}
