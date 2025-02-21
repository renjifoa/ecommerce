package com.onebox.ecommerce.repository;

import com.onebox.ecommerce.model.Cart;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CartRepository {
    private final Map<String, Cart> carts = new ConcurrentHashMap<>();

    public Cart getCartById(String cartId) {
        return carts.get(cartId);
    }

    public Cart saveCart(Cart cart) {
        carts.put(cart.getId(), cart);
        return cart;
    }

    public void deleteCart(String cartId) {
        carts.remove(cartId);
    }

    @Scheduled(fixedRate = 60000)
    public void deleteInactiveCarts() {
        LocalDateTime now = LocalDateTime.now();
        carts.entrySet().removeIf(entry ->
                Duration.between(entry.getValue().getLastUpdated(), now).toMinutes() >= 10
        );
    }
}
