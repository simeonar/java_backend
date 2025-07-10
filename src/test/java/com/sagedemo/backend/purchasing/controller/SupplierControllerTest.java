package com.sagedemo.backend.purchasing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sagedemo.backend.purchasing.service.SupplierService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SupplierController.class)
class SupplierControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SupplierService supplierService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @WithMockUser
    void testGetAllSuppliersReturnsEmptyList() throws Exception {
        when(supplierService.getAllSuppliers()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/purchasing/suppliers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
