package com.sagedemo.backend.sales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sagedemo.backend.sales.dto.CustomerDTO;
import com.sagedemo.backend.sales.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllCustomers() throws Exception {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(1L);
        dto.setCompanyName("Test Company");
        when(customerService.getAllCustomers()).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/sales/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value("Test Company"));
    }
}
