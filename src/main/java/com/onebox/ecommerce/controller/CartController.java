package com.onebox.ecommerce.controller;

import com.onebox.ecommerce.dto.ProductDto;
import com.onebox.ecommerce.exception.WebRestControllerAdvice;
import com.onebox.ecommerce.model.Cart;
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

/**
 * REST controller for managing shopping carts.
 * This controller provides endpoints for creating, retrieving, updating and deleting a cart by its ID.
 * It leverages the {@link CartService} for business logic and uses Swagger annotations
 * to document its endpoints.
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    /**
     * The service used to handle cart operations.
     */
    private final CartService cartService;

    /**
     * Constructs a new CartController with the specified CartService.
     *
     * @param cartService the service to be used for cart operations
     */
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Creates a new shopping cart.
     *
     * @return a ResponseEntity containing the newly created cart and a status of 201 (Created)
     */
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

    /**
     * Retrieves a cart by its ID.
     *
     * @param cartId the ID of the cart to retrieve
     * @return a ResponseEntity containing the cart and a status of 200 (OK) if found;
     *         otherwise, a 404 (Not Found) response is returned.
     */
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

    /**
     * Updates the products in a specified cart.
     *
     * @param cartId   the ID of the cart to update
     * @param products the list of products to update in the cart
     * @return a ResponseEntity containing the updated cart and a status of 200 (OK) if the cart is found;
     *         otherwise, a 404 (Not Found) response is returned.
     */
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
                                                       @Valid @RequestBody List<ProductDto> products) {
        Cart cart =  cartService.updateProductsFromCart(cartId, products);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    /**
     * Deletes a cart by its ID.
     *
     * @param cartId the ID of the cart to delete
     * @return a ResponseEntity with a status of 204 (No Content) if the deletion is successful;
     *         otherwise, a 404 (Not Found) response is returned.
     */
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
