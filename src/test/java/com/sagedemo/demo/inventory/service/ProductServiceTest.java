package com.sagedemo.demo.inventory.service;

import com.sagedemo.demo.inventory.dto.ProductDTO;
import com.sagedemo.demo.inventory.entity.Category;
import com.sagedemo.demo.inventory.entity.Product;
import com.sagedemo.demo.inventory.repository.CategoryRepository;
import com.sagedemo.demo.inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_shouldSaveAndReturnProductDTO() {
        ProductDTO dto = new ProductDTO();
        dto.setSku("SKU1");
        dto.setName("Test Product");
        dto.setDescription("Desc");
        dto.setPrice(BigDecimal.valueOf(100));
        dto.setUnit("pcs");
        dto.setCategoryId(1L);

        Category category = new Category();
        category.setId(1L);
        category.setName("Cat1");

        Product savedProduct = new Product();
        savedProduct.setId(10L);
        savedProduct.setSku("SKU1");
        savedProduct.setName("Test Product");
        savedProduct.setDescription("Desc");
        savedProduct.setPrice(BigDecimal.valueOf(100));
        savedProduct.setUnit("pcs");
        savedProduct.setCategory(category);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductDTO result = productService.createProduct(dto);
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("SKU1", result.getSku());
        assertEquals("Cat1", result.getCategoryName());
    }

    @Test
    void getAllProducts_shouldReturnListOfProductDTO() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setSku("SKU1");
        product1.setName("P1");
        product1.setDescription("D1");
        product1.setPrice(BigDecimal.valueOf(10));
        product1.setUnit("pcs");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setSku("SKU2");
        product2.setName("P2");
        product2.setDescription("D2");
        product2.setPrice(BigDecimal.valueOf(20));
        product2.setUnit("kg");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<ProductDTO> result = productService.getAllProducts();
        assertEquals(2, result.size());
        assertEquals("SKU1", result.get(0).getSku());
        assertEquals("SKU2", result.get(1).getSku());
    }

    @Test
    void createProduct_shouldThrowIfCategoryNotFound() {
        ProductDTO dto = new ProductDTO();
        dto.setCategoryId(99L);
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> productService.createProduct(dto));
        assertTrue(ex.getMessage().contains("Category not found"));
    }
}
