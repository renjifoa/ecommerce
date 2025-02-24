package com.onebox.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.onebox.ecommerce.dto.ProductDto;
import com.onebox.ecommerce.model.Cart;
import com.onebox.ecommerce.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UpdateCartControllerIntegrationTest {

    private static final String CART_ID_URI = "/cart/{cartId}";
    private static final String FIRST_PROD_DESC = "Apple";
    private static final String SECOND_PROD_DESC = "Banana";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cart cart;
    private ProductDto firstProduct;
    private ProductDto secondProduct;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        cart = cartService.createCart();

        firstProduct = new ProductDto(1L, 5);
        secondProduct = new ProductDto(2L, 2);
        cartService.updateProductsFromCart(cart.getId(), List.of(firstProduct, secondProduct));
    }

    @Test
    @DisplayName("Verify that can retrieve cart by id")
    void should_RetrieveCartById() throws Exception {

        mockMvc.perform(get(CART_ID_URI, cart.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cart.getId()))
                .andExpect(jsonPath("$.products.1.id").value(firstProduct.getId()))
                .andExpect(jsonPath("$.products.1.description").value(FIRST_PROD_DESC))
                .andExpect(jsonPath("$.products.1.amount").value(firstProduct.getAmount()))
                .andExpect(jsonPath("$.products.2.id").value(secondProduct.getId()))
                .andExpect(jsonPath("$.products.2.description").value(SECOND_PROD_DESC))
                .andExpect(jsonPath("$.products.2.amount").value(secondProduct.getAmount()))
                .andExpect(jsonPath("$.lastUpdated").exists());
    }

    @Test
    @DisplayName("Verify that can update products from cart")
    void should_UpdateProductsFromCart() throws Exception {

        ProductDto updatedProduct = new ProductDto(secondProduct.getId(), 7);
        cartService.updateProductsFromCart(cart.getId(), List.of(updatedProduct));

        mockMvc.perform(put(CART_ID_URI, cart.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(firstProduct, updatedProduct))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cart.getId()))
                .andExpect(jsonPath("$.products.1.id").value(firstProduct.getId()))
                .andExpect(jsonPath("$.products.1.description").value(FIRST_PROD_DESC))
                .andExpect(jsonPath("$.products.1.amount").value(firstProduct.getAmount()))
                .andExpect(jsonPath("$.products.2.id").value(secondProduct.getId()))
                .andExpect(jsonPath("$.products.2.description").value(SECOND_PROD_DESC))
                .andExpect(jsonPath("$.products.2.amount").value(updatedProduct.getAmount()))
                .andExpect(jsonPath("$.lastUpdated").exists());
    }
}
