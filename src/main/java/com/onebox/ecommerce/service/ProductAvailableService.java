package com.onebox.ecommerce.service;

import com.onebox.ecommerce.dto.ProductDto;
import com.onebox.ecommerce.exception.OutOfStockException;
import com.onebox.ecommerce.model.ProductAvailable;
import com.onebox.ecommerce.repository.ProductAvailableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing available products in the e-commerce system.
 * This class provides methods for retrieving available products from the
 * repository. The repository uses an in-memory data structure, a Map
 * to store and manage the products.
 */
@Service
public class ProductAvailableService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductAvailableService.class);
    private static final String PROD_NOT_STOCK_ERROR = "Product has not more stock for the id: %s";
    private static final String PROD_NOT_ENOUGH_ERROR = "Product has only %s stock for the id: %s";



    /**
     * The repository responsible for managing product availability data.
     * It uses an in-memory data structure to store products.
     */
    private final ProductAvailableRepository productAvailableRepository;

    /**
     * Constructs a new ProductAvailableService with the specified ProductAvailableRepository.
     *
     * @param productAvailableRepository the repository to use for managing product availability
     */
    @Autowired
    public ProductAvailableService(ProductAvailableRepository productAvailableRepository) {
        this.productAvailableRepository = productAvailableRepository;
    }

    /**
     * Retrieves all available products.
     * This method fetches a list of all products currently available in the system.
     *
     * @return a list of all available products
     */
    public List<ProductAvailable> getProducts() {
        LOGGER.info("Retrieving all products from the in-memory repository");
        return productAvailableRepository.getProducts();
    }

    /**
     * Retrieves an available product
     * This method retrieves a product that is available in the system using its
     * unique product ID is there is sufficient stock.
     *
     * @param productDTO the product data transfer object containing the product ID and requested amount
     * @return the available product if found and sufficient stock exists
     * @throws OutOfStockException if the product has no stock or insufficient stock
     */
    public ProductAvailable getProductCheckingTheStock(ProductDto productDTO) {
        LOGGER.info("Retrieving available product by id: {}", productDTO.getId());
        ProductAvailable prodAvailable = productAvailableRepository.getProductById(productDTO.getId());

        if (prodAvailable.getStock() == 0) {
            LOGGER.error("Product has not more stock for the id: {}", productDTO.getId());
            throw new OutOfStockException(PROD_NOT_STOCK_ERROR.formatted(productDTO.getId()));
        } else if (prodAvailable.getStock() < productDTO.getAmount()) {
            LOGGER.error("Product has only {} stock for the id: {}", prodAvailable.getStock(), productDTO.getId());
            throw new OutOfStockException(PROD_NOT_ENOUGH_ERROR.formatted(prodAvailable.getStock(),productDTO.getId()));
        }
        LOGGER.debug("Product exists for ID: {}", productDTO.getId());
        return prodAvailable;
    }
}
