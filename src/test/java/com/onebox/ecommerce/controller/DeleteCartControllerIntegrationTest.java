package com.onebox.ecommerce.controller;

import com.onebox.ecommerce.model.Cart;
import com.onebox.ecommerce.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class DeleteCartControllerIntegrationTest {

    private static final String DELETE_CART_URI = "/cart/{cartId}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartService cartService;

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = cartService.createCart();
    }

    @Test
    @DisplayName("Verify that cart is deleted successfully")
    void should_DeleteCartSuccessfully() throws Exception {
        mockMvc.perform(delete(DELETE_CART_URI, cart.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Verify that can not delete cart that does not exist")
    void should_CanNotDeleteCart_When_BadCartId() throws Exception {
        mockMvc.perform(delete(DELETE_CART_URI, 100L))
                .andExpect(status().isNotFound());
    }
}
