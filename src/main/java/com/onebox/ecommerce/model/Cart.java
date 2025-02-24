package com.onebox.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a shopping cart in the e-commerce system.
 * Each cart has a unique id, maintains a collection of products, and tracks the last time
 * it was updated. The cart is initialized with an empty product collection and the timestamp
 * of its creation.
 */
@Getter
@Setter
public class Cart {

    /**
     * Counter used to generate unique ids for each cart since
     * there isn't a database to ensure a unique ID for the carts.
     * I considered using a UUID, but I use Long in order to maintain
     * consistency with the numerical IDs used for the products.
     */
    private static Long counter = 0L;

    /**
     * Unique identifier for the cart.
     */
    private final Long id;

    /**
     * A map of products in the cart, where the key is the product id and the value is the product.
     * I decided to use a Map since there isn't a database and is more efficient
     * for working with get(), put(), remove(), etc. that a List.
     */
    private Map<Long, Product> products;

    /**
     * The timestamp of the last update made to the cart.
     */
    private LocalDateTime lastUpdated;

    /**
     * Constructs a new Cart instance with a unique id, an empty product map,
     * and the current time as the last updated timestamp.
     */
    public Cart() {
        this.id = ++counter;
        this.products = new HashMap<>();
        this.lastUpdated = LocalDateTime.now();
    }

    /**
     * Updates the cart's lastUpdated timestamp to the current time.
     */
    public void updateTimestamp() {
        this.lastUpdated = LocalDateTime.now();
    }
}
