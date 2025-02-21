package com.onebox.ecommerce.service;

import com.onebox.ecommerce.model.Cart;
import com.onebox.ecommerce.model.Product;
import com.onebox.ecommerce.repository.CartRepository;

import java.util.UUID;

public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart createCart() {
        Cart cart = new Cart(UUID.randomUUID().toString());
        cartRepository.saveCart(cart);
        return cart;
    }

    public Cart getCartById(String cartId) {
        return cartRepository.getCartById(cartId);
    }

    public void addProductToCart(String cartId, Product product) {
        Cart cart = cartRepository.getCartById(cartId);
        if (cart != null) {
            cart.addProduct(product);
            cartRepository.saveCart(cart);
        }
    }

    public void deleteProductToCart(String cartId, Product product) {
        Cart cart = cartRepository.getCartById(cartId);
        if (cart != null) {
            cart.deleteProduct(product);
            cartRepository.saveCart(cart);
        }
    }

    public void deleteCart(String cartId) {
        cartRepository.deleteCart(cartId);
    }

    public void deleteInactiveCarts() {
        cartRepository.deleteInactiveCarts();
    }
}
