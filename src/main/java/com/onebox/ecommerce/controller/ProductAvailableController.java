package com.onebox.ecommerce.controller;

import com.onebox.ecommerce.model.ProductAvailable;
import com.onebox.ecommerce.service.ProductAvailableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for product operations.
 * Provides endpoints to retrieve available products via {@link ProductAvailableService}.
 */
@RestController
@RequestMapping("/products")
public class ProductAvailableController {

    /**
     * The service used to manages product availability.
     */
    private final ProductAvailableService productAvailableService;

    /**
     * Constructs a new CartController with the specified CartService.
     *
     * @param productAvailableService the service to be used for cart operations
     */
    @Autowired
    public ProductAvailableController(ProductAvailableService productAvailableService) {
        this.productAvailableService = productAvailableService;
    }

    /**
     * Retrieves all available products.
     *
     * @return ResponseEntity containing the list of products and HTTP status
     */
    @Operation(summary = "Get all available products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductAvailable.class)))})
    })
    @GetMapping
    public ResponseEntity<List<ProductAvailable>> getAllProducts() {
        List<ProductAvailable> products = productAvailableService.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
