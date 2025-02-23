package com.onebox.ecommerce.repository;

import com.onebox.ecommerce.model.Cart;
import com.onebox.ecommerce.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository class that handles operations related to carts in the e-commerce system.
 *
 * This class manages cart data without using a database, utilizing an in-memory storage
 * mechanism (a Map of carts). It supports creating, updating, retrieving, and deleting
 * carts and products within them, as well as automatically removing inactive carts
 * after a set period of time.
 */
@Repository
public class CartRepository {

    /**
     * The inactivity limit in minutes. A cart is considered inactive if it has not been updated for this period.
     */
    private static final int INACTIVE_LIMIT = 10;

    /**
     * Logger for logging information and errors related to cart operations.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CartRepository.class);

    /**
     * Error message for when a cart is not found by its id.
     */
    private static final String CART_NOT_FOUND_ERROR = "Cart not found for the id: %s";

    /**
     * In-memory storage for the carts, mapping cart ids to Cart objects.
     */
    private final Map<Long, Cart> carts = new HashMap<>();

    /**
     * Retrieves a cart by its id.
     *
     * @param cartId the id of the cart to retrieve
     * @return the cart with the specified id
     * @throws IllegalArgumentException if the cart is not found
     */
    public Cart getCartById(Long cartId) {
        existsCartById(cartId);
        return carts.get(cartId);
    }

    /**
     * Checks if a cart with the given id exists in the system.
     *
     * @param cartId the id of the cart to check
     * @throws IllegalArgumentException if the cart does not exist
     */
    public void existsCartById(Long cartId) {
        if (!carts.containsKey(cartId)) {
            LOGGER.error("Cart not found for the id: {}", cartId);
            throw new IllegalArgumentException(CART_NOT_FOUND_ERROR.formatted(cartId));
        }
        LOGGER.debug("Cart exists for ID: {}", cartId);
    }

    /**
     * Updates the products in a cart. If the product amount is 0, it will be removed from the cart.
     * If the product exists or has a non-zero amount, it will be added or updated in the cart.
     *
     * @param cartId  the id of the cart to update
     * @param product the product to add or update in the cart
     */
    public void updateProduct(Long cartId, Product product) {
        Cart cart = getCartById(cartId);
        Map<Long, Product> products = cart.getProducts();
        if (product.getAmount() == 0 && products.containsKey(product.getId())) {
            products.remove(product.getId());
        } else {
            products.put(product.getId(), product);
        }
        cart.updateTimestamp();
    }

    /**
     * Saves a cart to the in-memory storage.
     *
     * @param cart the cart to save
     * @return the saved cart
     */
    public Cart saveCart(Cart cart) {
        carts.put(cart.getId(), cart);
        return cart;
    }

    /**
     * Deletes a cart by its id.
     *
     * @param cartId the id of the cart to delete
     * @throws IllegalArgumentException if the cart does not exist
     */
    public void deleteCart(Long cartId) {
        existsCartById(cartId);
        carts.remove(cartId);
    }

    /**
     * Deletes all carts that have been inactive for more than the specified inactivity limit.
     * Logs information for each cart that is deleted due to inactivity.
     */
    public void deleteInactiveCarts() {
        LocalDateTime now = LocalDateTime.now();

        carts.entrySet().removeIf(entry -> {
            boolean isInactive = Duration.between(entry.getValue().getLastUpdated(), now).toMinutes() >= INACTIVE_LIMIT;
            if (isInactive) {
                LOGGER.info("Cart was deleted due to inactivity with ID: {}", entry.getKey());
            }
            return isInactive;
        });
    }
}
