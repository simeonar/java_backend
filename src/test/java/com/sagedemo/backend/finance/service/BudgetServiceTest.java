package com.sagedemo.backend.finance.service;

import com.sagedemo.backend.finance.repository.BudgetRepository;
import com.sagedemo.backend.finance.entity.Budget;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BudgetServiceTest {
    @Mock
    private BudgetRepository budgetRepository;
    @InjectMocks
    private BudgetService budgetService;
    public BudgetServiceTest() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetAllBudgetsReturnsEmptyList() {
        when(budgetRepository.findAll()).thenReturn(Collections.emptyList());
        List<Budget> result = budgetService.getAllBudgets();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
