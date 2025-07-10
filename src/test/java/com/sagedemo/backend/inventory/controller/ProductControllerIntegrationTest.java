package com.sagedemo.backend.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sagedemo.backend.inventory.dto.ProductDTO;
import com.sagedemo.backend.inventory.entity.Category;
import com.sagedemo.backend.inventory.repository.CategoryRepository;
import com.sagedemo.backend.inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import({com.sagedemo.backend.inventory.TestProductConfig.class, com.sagedemo.backend.inventory.TestCategoryConfig.class})
@Transactional
class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private Long testCategoryId;

    @BeforeEach
    void setUp() {
        Category category = categoryRepository.findAll().stream().findFirst().orElse(null);
        assertThat(category).isNotNull();
        testCategoryId = category.getId();
    }

    @Test
    void createProduct_andGetAllProducts() throws Exception {
        ProductDTO dto = new ProductDTO();
        dto.setSku("SKU-INT-1");
        dto.setName("Integration Product");
        dto.setDescription("Integration test");
        dto.setPrice(BigDecimal.valueOf(123.45));
        dto.setUnit("pcs");
        dto.setCategoryId(testCategoryId);

        // Create product
        String response = mockMvc.perform(post("/api/inventory/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.sku").value("SKU-INT-1"))
                .andReturn().getResponse().getContentAsString();

        ProductDTO created = objectMapper.readValue(response, ProductDTO.class);
        assertThat(created.getName()).isEqualTo("Integration Product");
        assertThat(created.getCategoryId()).isEqualTo(testCategoryId);

        // Get all products
        mockMvc.perform(get("/api/inventory/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].sku").isArray())
                .andExpect(jsonPath("$.[*].sku").value(org.hamcrest.Matchers.hasItem("SKU-INT-1")));
    }

    @Test
    void createProduct_shouldReturnErrorIfCategoryNotFound() throws Exception {
        ProductDTO dto = new ProductDTO();
        dto.setSku("SKU-ERR");
        dto.setName("Error Product");
        dto.setCategoryId(999999L); // non-existent

        mockMvc.perform(post("/api/inventory/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError());
    }
}
