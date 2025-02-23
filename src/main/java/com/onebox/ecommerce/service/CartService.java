package com.onebox.ecommerce.service;

import com.onebox.ecommerce.model.Cart;
import com.onebox.ecommerce.model.Product;
import com.onebox.ecommerce.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing shopping carts in the e-commerce system.
 *
 * This class provides methods to create, retrieve, update, and delete carts,
 * as well as a scheduled task to remove inactive carts. It interacts with the
 * CartRepository to perform all data operations.
 */
@Service
public class CartService {

    /**
     * The fixed rate in milliseconds for running the scheduled task that deletes inactive carts.
     */
    private static final int SCHEDULED_TIME_MS = 60000;

    /**
     * Logger for logging information related to cart operations.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    /**
     * The repository that manages cart storage.
     */
    private final CartRepository cartRepository;

    /**
     * Constructs a new CartService with the specified CartRepository.
     *
     * @param cartRepository the repository to use for managing carts
     */
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * Creates a new cart and saves it in the repository.
     *
     * @return the newly created Cart
     */
    public Cart createCart() {
        Cart cartCreated = cartRepository.saveCart(new Cart());
        LOGGER.info("Created new cart with ID: {}", cartCreated.getId());
        return cartCreated;
    }

    /**
     * Retrieves a cart by its id.
     *
     * @param cartId the id of the cart to retrieve
     * @return the Cart with the specified id
     */
    public Cart getCartById(Long cartId) {
        LOGGER.info("Retrieving cart with ID: {}", cartId);
        return cartRepository.getCartById(cartId);
    }

    /**
     * Updates the products in a cart.
     *
     * This method updates the products of the cart by iterating through the provided list
     * and delegating the update operation to the CartRepository. The updated cart is then saved.
     *
     * @param cartId   the id of the cart to update
     * @param products the list of products to update in the cart
     * @return the updated Cart
     */
    public Cart updateProductsFromCart(Long cartId, List<Product> products) {
        LOGGER.info("Updating products for cart with ID: {}", cartId);
        Cart cart = cartRepository.getCartById(cartId);

        for (Product p : products) {
            cartRepository.updateProduct(cartId, p);
        }
        return cartRepository.saveCart(cart);
    }

    /**
     * Deletes a cart by its id.
     *
     * @param cartId the id of the cart to delete
     */
    public void deleteCart(Long cartId) {
        LOGGER.info("Deleting cart with ID: {}", cartId);
        cartRepository.deleteCart(cartId);
    }

    /**
     * Scheduled task that deletes inactive carts.
     *
     * This method runs at a fixed rate (every 60 seconds) and removes all carts
     * that have been inactive for a period greater than the defined limit.
     */
    @Scheduled(fixedRate = SCHEDULED_TIME_MS)
    public void deleteInactiveCarts() {
        cartRepository.deleteInactiveCarts();
    }
}
