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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CartServiceIntegrationTest {
    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_DESC = "Product A";

    private CartService cartService;

    private Cart cart;
    private Long cartId;

    @BeforeEach
    void setUp() {
        CartRepository cartRepository = new CartRepository();
        cartService = new CartService(cartRepository);

        cart = cartService.createCart();
        cartId = cart.getId();
    }

    @Test
    @DisplayName("Verify that cart is created successfully")
    void should_CreateCart_When_IsCalled() {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(cart).as("Cart should not be null").isNotNull();
        softAssertions.assertThat(cart.getId()).as("CartId should not be null").isNotNull();
        softAssertions.assertThat(cart.getProducts()).as("Cart should be empty").isEqualTo(Map.of());
        softAssertions.assertThat(cart.getLastUpdated()).as("Cart should have LastUpdated")
                .isBetween(LocalDateTime.now().minusSeconds(10), LocalDateTime.now());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Verify that a product is added to the cart correctly")
    void should_AddProductToCart_When_IsCalled() {
        cartService.updateProductsFromCart(cartId, getListOfProducts());

        Map<Long, Product> actualProducts = cartService.getCartById(cartId).getProducts();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualProducts.size()).as("Should be 1 product").isEqualTo(1);
        softAssertions.assertThat(actualProducts.get(PRODUCT_ID).getId())
                .as("ProductId should not be null").isEqualTo(PRODUCT_ID);
        softAssertions.assertThat(actualProducts.get(PRODUCT_ID).getAmount())
                .as("Amount should be equal").isEqualTo(2);
        softAssertions.assertThat(actualProducts.get(PRODUCT_ID).getDescription())
                .as("Description should be equal").isEqualTo(PRODUCT_DESC);
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Verify that a product is updated to the cart correctly")
    void should_UpdateProductToCart_When_IsCalled() {
        cartService.updateProductsFromCart(cartId, getListOfProducts());

        List<Product> productsToUpdate = List.of(new Product(PRODUCT_ID, PRODUCT_DESC, 5));
        cartService.updateProductsFromCart(cartId, productsToUpdate);

        Map<Long, Product> actualProducts = cartService.getCartById(cartId).getProducts();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualProducts.size()).as("Should be 1 product").isEqualTo(1);
        softAssertions.assertThat(actualProducts.get(PRODUCT_ID).getId())
                .as("ProductId should not be null").isEqualTo(PRODUCT_ID);
        softAssertions.assertThat(actualProducts.get(PRODUCT_ID).getAmount())
                .as("Amount should be equal").isEqualTo(5);
        softAssertions.assertThat(actualProducts.get(PRODUCT_ID).getDescription())
                .as("Description should be equal").isEqualTo(PRODUCT_DESC);
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Verify that a product is deleted from the cart correctly")
    void should_DeleteProductFromCart_When_IsCalled() {
        cartService.updateProductsFromCart(cartId, getListOfProducts());

        List<Product> productsToUpdate = List.of(new Product(PRODUCT_ID, PRODUCT_DESC, 0));
        cartService.updateProductsFromCart(cartId, productsToUpdate);

        assertThat(cartService.getCartById(cartId).getProducts()).as("Should be empty").isEqualTo(Map.of());
    }

    @Test
    @DisplayName("Verify that lastUpdated from cart is correctly updated")
    void should_LastUpdatedCart_When_IsCalled() {
        cartService.updateProductsFromCart(cartId, getListOfProducts());
        cart.setLastUpdated(LocalDateTime.now().minusMinutes(7));

        List<Product> productsToUpdate = List.of(new Product(PRODUCT_ID, PRODUCT_DESC, 5));
        cartService.updateProductsFromCart(cartId, productsToUpdate);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(cartService.getCartById(cartId).getLastUpdated())
                .as("LastUpdated() should be updated")
                .isBetween(LocalDateTime.now().minusMinutes(1), LocalDateTime.now());

        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Verify that a cart is deleted successfully")
    void should_DeleteTheCart_When_IsCalled() {
        cartId = cart.getId();
        cartService.deleteCart(cartId);

        assertThatCode( () -> cartService.getCartById(cartId) )
                .as("Cart should thrown an exception")
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Cart not found for the id: " + cartId);
    }

    @Test
    @DisplayName("Verify that the carts inactive are deleted")
    void should_DeleteCarts_When_AreInactive() {
        Cart inactiveCart = cartService.createCart();
        inactiveCart.setLastUpdated(LocalDateTime.now().minusMinutes(12));

        cartService.deleteInactiveCarts();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(cartService.getCartById(cartId))
                .as("Cart active should not be null").isNotNull();
        softAssertions.assertThatCode( () -> cartService.getCartById(inactiveCart.getId()) )
                .as("Cart inactive should thrown an exception").isInstanceOf(RuntimeException.class);
        softAssertions.assertAll();
    }

    private List<Product> getListOfProducts() {
        return List.of(new Product(PRODUCT_ID, PRODUCT_DESC, 2));
    }
}
