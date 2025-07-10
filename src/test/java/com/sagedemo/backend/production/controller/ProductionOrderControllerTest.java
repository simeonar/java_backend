package com.sagedemo.backend.production.controller;

import com.sagedemo.backend.production.entity.BillOfMaterials;
import com.sagedemo.backend.production.entity.ProductionOrder;
import com.sagedemo.backend.production.service.ProductionOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductionOrderController.class)
class ProductionOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductionOrderService productionOrderService;

    @Test
    @org.springframework.security.test.context.support.WithMockUser
    void createOrder_success() throws Exception {
        ProductionOrder order = new ProductionOrder();
        order.setId(1L);
        Mockito.when(productionOrderService.createProductionOrder(eq(2L), eq(10))).thenReturn(order);
        mockMvc.perform(post("/api/production/orders")
                .param("bomId", "2")
                .param("quantity", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser
    void getAllOrders_success() throws Exception {
        ProductionOrder order = new ProductionOrder();
        order.setId(5L);
        Mockito.when(productionOrderService.getAllProductionOrders()).thenReturn(List.of(order));
        mockMvc.perform(get("/api/production/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(5L));
    }
}
