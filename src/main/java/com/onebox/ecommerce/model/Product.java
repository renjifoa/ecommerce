package com.onebox.ecommerce.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * Represents a product in the e-commerce system.
 * A product is uniquely identified by its id and contains a description
 * along with an amount. I assume that is not necessary having a list of existing products since there isn't a database.
 * Validation annotations ensure that the id and amount fields are not null.
 */
@Getter
public class Product {

    /**
     * Unique identifier for the product.
     */
    @NotNull
    private final Long id;

    /**
     * Description of the product.
     */
    private final String description;

    /**
     * Quantity associated with the product. I assume that this represents the quantity rather than the price.
     */
    @NotNull
    @Min(0)
    private final Integer amount;

    /**
     * Constructs a new Product instance.
     *
     * @param id the unique identifier for the product; must not be null
     * @param description the description of the product
     * @param amount the amount associated with the quantity of the product; must not be null
     */
    public Product(Long id, String description, Integer amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
    }
}
