package com.onebox.ecommerce.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class Product {
    @NotNull
    private final Long id;
    private final String description;
    @NotNull
    private final Integer amount;

    public Product(Long id, String description, Integer amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
    }
}
