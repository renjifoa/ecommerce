package com.onebox.ecommerce.service;

import com.onebox.ecommerce.dto.ProductDto;
import com.onebox.ecommerce.exception.OutOfStockException;
import com.onebox.ecommerce.model.ProductAvailable;
import com.onebox.ecommerce.repository.ProductAvailableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductAvailableServiceTest {

    @Mock
    private ProductAvailableRepository productRepository;

    @InjectMocks
    private ProductAvailableService productService;
    private ProductDto productDTO;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDto(1L, 5);
    }

    @Test
    @DisplayName("Verify that throws an exception when there isn't stock available")
    void should_ThrowException_When_NoStockIsAvailable() {

        ProductAvailable productAvailable = new ProductAvailable(1L, "Apple", 0);

        when(productRepository.getProductById(productDTO.getId())).thenReturn(productAvailable);
        assertThrows(OutOfStockException.class, () -> productService.getProductCheckingTheStock(productDTO));
    }

    @Test
    @DisplayName("Verify that throws an exception when there isn't sufficient stock available")
    void should_ThrowException_When_NoSufficientStockIsAvailable() {

        ProductAvailable productAvailable = new ProductAvailable(1L, "Apple", 2);

        when(productRepository.getProductById(productDTO.getId())).thenReturn(productAvailable);
        assertThrows(OutOfStockException.class, () -> productService.getProductCheckingTheStock(productDTO));
    }
}
