package com.sagedemo.backend.finance.service;

import com.sagedemo.backend.finance.entity.Account;
import com.sagedemo.backend.finance.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountService accountService;
    public AccountServiceTest() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetAllAccountsReturnsEmptyList() {
        when(accountRepository.findAll()).thenReturn(Collections.emptyList());
        List<Account> result = accountService.getAllAccounts();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
