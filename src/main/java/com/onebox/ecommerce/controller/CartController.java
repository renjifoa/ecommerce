package com.onebox.ecommerce.controller;

import com.onebox.ecommerce.model.Cart;
import com.onebox.ecommerce.model.Product;
import com.onebox.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Create a new cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cart created",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Cart.class)) })
    })
    @PostMapping
    public ResponseEntity<Cart> createCart() {
        Cart cart = cartService.createCart();
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a cart by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Cart.class)) }),
           @ApiResponse(responseCode = "404", description = "Cart not found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = WebRestControllerAdvice.ErrorResponse.class)) })
    })
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) {
        Cart cart =  cartService.getCartById(cartId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @Operation(summary = "Update products in a cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart updated",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Cart.class)) }),
            @ApiResponse(responseCode = "404", description = "Cart not found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = WebRestControllerAdvice.ErrorResponse.class)) })
    })
    @PutMapping("/{cartId}")
    public ResponseEntity<Cart> updateProductsFromCart(@PathVariable Long cartId,
                                                       @Valid @RequestBody List<Product> products) {
        Cart cart =  cartService.updateProductsFromCart(cartId, products);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @Operation(summary = "Delete a cart by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cart not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WebRestControllerAdvice.ErrorResponse.class)) })
    })
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Cart> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
