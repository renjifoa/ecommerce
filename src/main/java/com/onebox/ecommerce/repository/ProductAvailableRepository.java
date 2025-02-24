package com.onebox.ecommerce.repository;

import com.onebox.ecommerce.model.ProductAvailable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository class for managing product availability in the e-commerce system.
 * This repository uses an in-memory data structure, a Map, to store and manage
 * the available products. Each product is represented by a ProductAvailable object.
 * The repository is initialized with a predefined list of product names and assigns
 * unique IDs to each product. The initial stock for each product is set to 50 units.
 */
@Repository
public class ProductAvailableRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductAvailableRepository.class);
    private static final String PRODUCT_NOT_FOUND_ERROR = "Product not found for the id: %s";

    /**
     * The in-memory storage for products.
     */
    private final Map<Long, ProductAvailable> products;

    /**
     * Initializes the repository with a predefined list of product names and assigns
     * unique IDs to each product. The product stock is initially set to 50 for each product.
     * Calls method initProducts() to initialize products
     */
    public ProductAvailableRepository() {
        this.products = new HashMap<>();
        initProducts();
    }

    /**
     * Retrieves an available product by its ID.
     * This method checks if the product exists, and if so, returns it. If the product
     * does not exist, it throws an IllegalArgumentException.
     *
     * @param productId the ID of the product to retrieve
     * @return the {@link ProductAvailable} with the specified ID
     * @throws IllegalArgumentException if the product is not found
     */
    public ProductAvailable getProductById(Long productId) {
        existsProductById(productId);
        return products.get(productId);
    }

    /**
     * Retrieves all available products.
     * This method returns a list of all products available in the repository.
     *
     * @return a list of all available {@link ProductAvailable} objects
     */
    public List<ProductAvailable> getProducts() {
        return products.values().stream()
                .filter( product -> product.getStock() > 0)
                .toList();
    }

    /**
     * Checks if a product exists by its ID. If the product does not exist, it throws
     * an IllegalArgumentException.
     *
     * @param productId the ID of the product to check
     * @throws IllegalArgumentException if the product does not exist
     */
    public void existsProductById(Long productId) {
        if (!products.containsKey(productId)) {
            LOGGER.error("Product not found for the id: {}", productId);
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND_ERROR.formatted(productId));
        }
        LOGGER.debug("Product exists for ID: {}", productId);
    }

    /**
     * Initializes the in-memory product list with predefined products.
     * This method assigns a unique ID to each product and sets the initial stock to 50.
     */
    private void initProducts() {
        List<String> productNames = List.of("Apple", "Banana", "Orange", "Mango", "Pineapple",
                "Watermelon", "Papaya", "Peach", "Kiwi", "Avocado");
        long countter = 0L;
        for (String name : productNames) {
            ProductAvailable productAvailable = new ProductAvailable(++countter, name, (int) countter * 100);
            products.put(productAvailable.getId(), productAvailable);
        }
    }
}
