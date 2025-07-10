package com.sagedemo.demo.sales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sagedemo.demo.inventory.entity.Product;
import com.sagedemo.demo.inventory.repository.ProductRepository;
import com.sagedemo.demo.sales.dto.SalesOrderDTO;
import com.sagedemo.demo.sales.dto.SalesOrderItemDTO;
import com.sagedemo.demo.sales.entity.Customer;
import com.sagedemo.demo.sales.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SalesOrderControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    private Long testCustomerId;
    private Long testProductId;

    @BeforeEach
    void setUp() {
        // Ensure at least one customer and product exist
        Customer customer = customerRepository.findAll().stream().findFirst().orElseGet(() -> {
            Customer c = new Customer();
            c.setCompanyName("Test Customer");
            return customerRepository.save(c);
        });
        testCustomerId = customer.getId();
        Product product = productRepository.findAll().stream().findFirst().orElseGet(() -> {
            Product p = new Product();
            p.setName("Test Product");
            p.setSku("SKU-SALES-1");
            p.setPrice(BigDecimal.valueOf(50));
            p.setUnit("pcs");
            return productRepository.save(p);
        });
        testProductId = product.getId();
    }

    @Test
    void createSalesOrder_andGetAllSalesOrders() throws Exception {
        SalesOrderItemDTO item = new SalesOrderItemDTO();
        item.setProductId(testProductId);
        item.setQuantity(2);
        SalesOrderDTO dto = new SalesOrderDTO();
        dto.setCustomerId(testCustomerId);
        dto.setItems(List.of(item));

        // Create order
        String response = mockMvc.perform(post("/api/sales/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.customerId").value(testCustomerId))
                .andReturn().getResponse().getContentAsString();

        SalesOrderDTO created = objectMapper.readValue(response, SalesOrderDTO.class);
        assertThat(created.getItems()).hasSize(1);
        assertThat(created.getItems().get(0).getProductId()).isEqualTo(testProductId);

        // Get all orders
        mockMvc.perform(get("/api/sales/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").isArray())
                .andExpect(jsonPath("$.[*].customerId").value(org.hamcrest.Matchers.hasItem(testCustomerId.intValue())));
    }

    @Test
    void createSalesOrder_shouldReturnErrorIfCustomerNotFound() throws Exception {
        SalesOrderItemDTO item = new SalesOrderItemDTO();
        item.setProductId(testProductId);
        item.setQuantity(1);
        SalesOrderDTO dto = new SalesOrderDTO();
        dto.setCustomerId(999999L);
        dto.setItems(List.of(item));
        mockMvc.perform(post("/api/sales/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void createSalesOrder_shouldReturnErrorIfProductNotFound() throws Exception {
        SalesOrderItemDTO item = new SalesOrderItemDTO();
        item.setProductId(999999L);
        item.setQuantity(1);
        SalesOrderDTO dto = new SalesOrderDTO();
        dto.setCustomerId(testCustomerId);
        dto.setItems(List.of(item));
        mockMvc.perform(post("/api/sales/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError());
    }
}
