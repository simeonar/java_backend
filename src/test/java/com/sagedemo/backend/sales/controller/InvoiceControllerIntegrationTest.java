package com.sagedemo.backend.sales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sagedemo.backend.sales.entity.SalesOrder;
import com.sagedemo.backend.sales.repository.InvoiceRepository;
import com.sagedemo.backend.sales.repository.SalesOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sagedemo.backend.finance.TestFinanceAccountConfig;
import org.springframework.context.annotation.Import;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Import(TestFinanceAccountConfig.class)
class InvoiceControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SalesOrderRepository salesOrderRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private com.sagedemo.backend.sales.repository.CustomerRepository customerRepository;

    private Long testSalesOrderId;

    @BeforeEach
    void setUp() {
        // Ensure at least one customer exists
        com.sagedemo.backend.sales.entity.Customer customer = customerRepository.findAll().stream().findFirst().orElseGet(() -> {
            com.sagedemo.backend.sales.entity.Customer c = new com.sagedemo.backend.sales.entity.Customer();
            c.setCompanyName("Test Customer");
            return customerRepository.save(c);
        });
        // Ensure at least one sales order exists with customer
        SalesOrder order = salesOrderRepository.findAll().stream().filter(o -> o.getCustomer() != null).findFirst().orElseGet(() -> {
            SalesOrder o = new SalesOrder();
            o.setCustomer(customer);
            o.setTotalAmount(BigDecimal.valueOf(123.45));
            return salesOrderRepository.save(o);
        });
        testSalesOrderId = order.getId();
    }

    @Test
    void createInvoice_andGetAllInvoices() throws Exception {
        // Create invoice
        String response = mockMvc.perform(post("/api/sales/invoices")
                .param("salesOrderId", testSalesOrderId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.salesOrderId").value(testSalesOrderId))
                .andReturn().getResponse().getContentAsString();

        assertThat(response).contains("invoiceNumber");

        // Get all invoices
        mockMvc.perform(get("/api/sales/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].salesOrderId").isArray())
                .andExpect(jsonPath("$.[*].salesOrderId").value(org.hamcrest.Matchers.hasItem(testSalesOrderId.intValue())));
    }

    @Test
    void createInvoice_shouldReturnErrorIfOrderNotFound() throws Exception {
        mockMvc.perform(post("/api/sales/invoices")
                .param("salesOrderId", "999999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
