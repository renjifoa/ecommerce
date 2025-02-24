package com.onebox.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for representing a product in the e-commerce system.
 *
 * This class is used to transfer product data from the request that only contains
 * the product's unique identifier (ID) and the amount requested by the user.
 */
@Getter
public class ProductDto {

    /**
     * The unique identifier of the product.
     */
    @NotNull
    private final Long id;

    /**
     * The amount of the product requested.
     */
    @NotNull
    @Min(0)
    private final Integer amount;

    /**
     * Constructs a new {@link ProductDto} instance with the specified ID and amount.
     *
     * @param id the unique identifier of the product
     * @param amount the amount of the product requested
     */
    public ProductDto(Long id, Integer amount) {
        this.id = id;
        this.amount = amount;
    }
}
