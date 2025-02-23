package com.onebox.ecommerce.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CreateCartControllerIntegrationTest {
    private static final String CREATE_CART_URI = "/cart";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Verify that cart is created successfully")
    void should_CreateCartSuccessfully() throws Exception {
        mockMvc.perform(post(CREATE_CART_URI))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.products").isEmpty())
                .andExpect(jsonPath("$.lastUpdated").exists());
    }

}
