package com.onebox.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Cart {
    private static Long counter = 0L;

    private final Long id;
    private Map<Long, Product> products;
    private LocalDateTime  lastUpdated;

    public Cart() {
        this.id = ++counter;
        this.products = new HashMap<>();
        this.lastUpdated = LocalDateTime.now();
    }

    public void updateTimestamp() {
        this.lastUpdated = LocalDateTime.now();
    }
}
