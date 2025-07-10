package com.sagedemo.demo.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sagedemo.demo.inventory.entity.StockMovement;
import com.sagedemo.demo.inventory.entity.Product;
import com.sagedemo.demo.inventory.entity.Category;
import com.sagedemo.demo.inventory.repository.StockMovementRepository;
import com.sagedemo.demo.inventory.repository.ProductRepository;
import com.sagedemo.demo.inventory.repository.CategoryRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Import({com.sagedemo.demo.inventory.TestProductConfig.class, com.sagedemo.demo.inventory.TestCategoryConfig.class})
@AutoConfigureMockMvc
@Transactional
class StockMovementControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StockMovementRepository stockMovementRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private Product testProduct;
    @Autowired
    private Category testCategory;

    private StockMovement movement;

    @BeforeEach
    void setUp() {
        movement = new StockMovement();
        movement.setProduct(testProduct);
        movement.setQuantity(5);
        movement.setType(StockMovement.MovementType.INBOUND);
        movement.setReference("test-ref");
        stockMovementRepository.save(movement);
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/api/stock-movements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(5));
    }

    @Test
    void testGetById() throws Exception {
        mockMvc.perform(get("/api/stock-movements/" + movement.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    void testCreate() throws Exception {
        StockMovement newMovement = new StockMovement();
        newMovement.setProduct(testProduct);
        newMovement.setQuantity(10);
        newMovement.setType(StockMovement.MovementType.OUTBOUND);
        newMovement.setReference("new-ref");
        mockMvc.perform(post("/api/stock-movements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMovement)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    void testUpdate() throws Exception {
        movement.setQuantity(20);
        mockMvc.perform(put("/api/stock-movements/" + movement.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movement)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(20));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/stock-movements/" + movement.getId()))
                .andExpect(status().isNoContent());
    }
}
