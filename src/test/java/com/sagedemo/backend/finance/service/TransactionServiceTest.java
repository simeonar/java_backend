package com.sagedemo.backend.finance.service;

import com.sagedemo.backend.finance.repository.TransactionRepository;
import com.sagedemo.backend.finance.repository.AccountRepository;
import com.sagedemo.backend.finance.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private TransactionService transactionService;
    public TransactionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetAllTransactionsReturnsEmptyList() {
        when(transactionRepository.findAll()).thenReturn(Collections.emptyList());
        List<Transaction> result = transactionService.getAllTransactions();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
