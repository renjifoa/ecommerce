package com.onebox.ecommerce.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a product available in the e-commerce system.
 * This class is used to define the details of a product that is available for purchase,
 * such as its unique identifier (ID), description, and available stock.
 */
@Setter
@Getter
public class ProductAvailable {

    /**
     * The unique identifier of the product.
     */
    @NotNull
    private final Long id;

    /**
     * The description of the product.
     */
    private final String description;

    /**
     * The amount of the product available in stock.
     */
    @NotNull
    @Min(0)
    private final Integer stock;

    /**
     * Constructs a new ProductAvailable instance with the specified ID, description, and stock.
     *
     * @param id the unique identifier of the product
     * @param description the description of the product
     * @param stock the amount of the product available in stock
     */
    public ProductAvailable(Long id, String description, Integer stock) {
        this.id = id;
        this.description = description;
        this.stock = stock;
    }
}
