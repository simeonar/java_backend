package com.sagedemo.backend.finance.controller;

import com.sagedemo.backend.finance.service.BudgetService;
import com.sagedemo.backend.finance.entity.Budget;
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

@WebMvcTest(BudgetController.class)
class BudgetControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BudgetService budgetService;
    @Test
    @WithMockUser
    void testGetAllBudgetsReturnsEmptyList() throws Exception {
        when(budgetService.getAllBudgets()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/finance/budgets"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
