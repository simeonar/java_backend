package com.sagedemo.demo.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sagedemo.demo.inventory.dto.WarehouseDTO;
import com.sagedemo.demo.inventory.repository.WarehouseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class WarehouseControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test
    void createWarehouse_andGetAllWarehouses() throws Exception {
        WarehouseDTO dto = new WarehouseDTO();
        dto.setName("Test Warehouse");
        dto.setLocation("Saint-Petersburg");

        // Create warehouse
        String response = mockMvc.perform(post("/api/inventory/warehouses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Warehouse"))
                .andReturn().getResponse().getContentAsString();

        WarehouseDTO created = objectMapper.readValue(response, WarehouseDTO.class);
        assertThat(created.getLocation()).isEqualTo("Saint-Petersburg");

        // Get all warehouses
        mockMvc.perform(get("/api/inventory/warehouses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").isArray())
                .andExpect(jsonPath("$.[*].name").value(org.hamcrest.Matchers.hasItem("Test Warehouse")));
    }
}
