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

@Repository
public class CartRepository {
    private static final int INACTIVE_LIMIT = 10;
    private static final Logger LOGGER = LoggerFactory.getLogger(CartRepository.class);
    private static final String CART_NOT_FOUND_ERROR = "Cart not found for the id: %s";

    private final Map<Long, Cart> carts = new HashMap<>();

    public Cart getCartById(Long cartId) {
        existsCartById(cartId);
        return carts.get(cartId);
    }

    public void existsCartById(Long cartId) {
        if (!carts.containsKey(cartId)) {
            LOGGER.error("Cart not found for the id: {}", cartId);
            throw new IllegalArgumentException(CART_NOT_FOUND_ERROR.formatted(cartId));
        }
        LOGGER.debug("Cart exists for ID: {}", cartId);
    }

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

    public Cart saveCart(Cart cart) {
        carts.put(cart.getId(), cart);
        return cart;
    }

    public void deleteCart(Long cartId) {
        existsCartById(cartId);
        carts.remove(cartId);
    }

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
