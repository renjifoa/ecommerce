package com.onebox.ecommerce.service;

import com.onebox.ecommerce.model.Cart;
import com.onebox.ecommerce.model.Product;
import com.onebox.ecommerce.repository.CartRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CartServiceIT {
    private CartService cartService;

    @BeforeEach
    void setUp() {
        CartRepository cartRepository = new CartRepository();
        cartService = new CartService(cartRepository);
    }

    @Test
    @DisplayName("Verify that cart is created successfully")
    void should_CreateCart_When_IsCalled() {
        Cart cart = cartService.createCart();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(cart).as("Cart should not be null").isNotNull();
        softAssertions.assertThat(cart.getId()).as("CartId should not be null").isNotNull();
        softAssertions.assertThat(cart.getProducts()).as("Cart should be empty").isEqualTo(List.of());
        softAssertions.assertThat(cart.getLastUpdated()).as("Cart should have LastUpdated").isBetween(LocalDateTime.now().minusSeconds(10), LocalDateTime.now());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Verify that a product is added to the cart correctly")
    void should_AddProductToCart_When_IsCalled() {
        Cart cart = cartService.createCart();
        Product product = new Product(1L, "Product A", 25.0);

        cartService.addProductToCart(cart.getId(), product);

        List<Product> actualProducts = cartService.getCartById(cart.getId()).getProducts();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualProducts.size()).as("Should be 1 product").isEqualTo(1);
        softAssertions.assertThat(actualProducts.get(0).getId()).as("ProductId should not be null").isEqualTo(1L);
        softAssertions.assertThat(actualProducts.get(0).getAmount()).as("Amount should be equal").isEqualTo(25.0);
        softAssertions.assertThat(actualProducts.get(0).getDescription()).as("Description should be equal").isEqualTo("Product A");
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Verify that a product is deleted to the cart correctly")
    void should_DeleteProductToCart_When_IsCalled() {
        Cart cart = cartService.createCart();
        Product product = new Product(1L, "Product A", 25.0);

        cartService.addProductToCart(cart.getId(), product);
        cartService.deleteProductToCart(cart.getId(), product);

        assertThat(cartService.getCartById(cart.getId()).getProducts()).as("Should be empty").isEqualTo(List.of());
    }

    @Test
    @DisplayName("Verify that a cart is deleted successfully")
    void should_DeleteTheCart_When_IsCalled() {
        Cart cart = cartService.createCart();

        cartService.deleteCart(cart.getId());

        assertThat(cartService.getCartById(cart.getId())).as("Cart should be null").isNull();
    }

    @Test
    @DisplayName("Verify that the carts inactive are deleted")
    void should_DeleteCarts_When_AreInactive() {
        Cart cart = cartService.createCart();

        Cart inactiveCart = cartService.createCart();
        inactiveCart.setLastUpdated(LocalDateTime.now().minusMinutes(12));

        cartService.deleteInactiveCarts();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(cartService.getCartById(cart.getId())).as("Cart active should not be null").isNotNull();
        softAssertions.assertThat(cartService.getCartById(inactiveCart.getId())).as("Cart inactive should be null").isNull();
        softAssertions.assertAll();
    }
}
