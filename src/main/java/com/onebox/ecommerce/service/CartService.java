package com.onebox.ecommerce.service;

import com.onebox.ecommerce.model.Cart;
import com.onebox.ecommerce.model.Product;
import com.onebox.ecommerce.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private static final int SCHEDULED_TIME_MS = 60000;
    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart createCart() {
        Cart cartCreated = cartRepository.saveCart(new Cart());
        LOGGER.info("Created new cart with ID: {}", cartCreated.getId());
        return cartCreated;
    }

    public Cart getCartById(Long cartId) {
        LOGGER.info("Retrieving cart with ID: {}", cartId);
        return cartRepository.getCartById(cartId);
    }

    public Cart updateProductsFromCart(Long cartId, List<Product> products) {
        LOGGER.info("Updating products from cart with ID: {}", cartId);
        Cart cart = cartRepository.getCartById(cartId);

        for (Product p: products) {
            cartRepository.updateProduct(cartId, p);
        }
        return cartRepository.saveCart(cart);
    }

    public void deleteCart(Long cartId) {
        LOGGER.info("Deleting cart with ID: {}", cartId);
        cartRepository.deleteCart(cartId);
    }

    @Scheduled(fixedRate = SCHEDULED_TIME_MS)
    public void deleteInactiveCarts() {
        cartRepository.deleteInactiveCarts();
    }
}
