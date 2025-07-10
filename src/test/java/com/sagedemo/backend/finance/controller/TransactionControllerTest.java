package com.sagedemo.backend.finance.controller;

import com.sagedemo.backend.finance.service.TransactionService;
import com.sagedemo.backend.finance.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private com.sagedemo.backend.finance.service.AccountService accountService;
    @Test
    @WithMockUser
    void testGetAllTransactionsReturnsEmptyList() throws Exception {
        when(transactionService.getAllTransactions()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/finance/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
