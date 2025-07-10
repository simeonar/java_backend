package com.sagedemo.backend.finance.service;

import com.sagedemo.backend.finance.dto.BalanceReportDTO;
import com.sagedemo.backend.finance.dto.ProfitLossReportDTO;
import com.sagedemo.backend.finance.entity.Account;
import com.sagedemo.backend.finance.entity.AccountType;
import com.sagedemo.backend.finance.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FinancialReportServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private FinancialReportService financialReportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBalanceReport_returnsCorrectTotals() {
        Account asset = new Account();
        asset.setType(AccountType.ASSET);
        asset.setAccountName("Cash");
        asset.setBalance(BigDecimal.valueOf(100));
        Account liability = new Account();
        liability.setType(AccountType.LIABILITY);
        liability.setAccountName("Loan");
        liability.setBalance(BigDecimal.valueOf(50));
        Account equity = new Account();
        equity.setType(AccountType.EQUITY);
        equity.setAccountName("Capital");
        equity.setBalance(BigDecimal.valueOf(30));
        when(accountRepository.findAll()).thenReturn(List.of(asset, liability, equity));
        BalanceReportDTO dto = financialReportService.getBalanceReport();
        assertEquals(BigDecimal.valueOf(100), dto.getTotalAssets());
        assertEquals(BigDecimal.valueOf(50), dto.getTotalLiabilities());
        assertEquals(BigDecimal.valueOf(30), dto.getTotalEquity());
    }

    @Test
    void getProfitLossReport_returnsCorrectTotals() {
        Account revenue = new Account();
        revenue.setType(AccountType.REVENUE);
        revenue.setAccountName("Sales");
        revenue.setBalance(BigDecimal.valueOf(200));
        Account expense = new Account();
        expense.setType(AccountType.EXPENSE);
        expense.setAccountName("Rent");
        expense.setBalance(BigDecimal.valueOf(80));
        when(accountRepository.findAll()).thenReturn(List.of(revenue, expense));
        ProfitLossReportDTO dto = financialReportService.getProfitLossReport();
        assertEquals(BigDecimal.valueOf(200), dto.getTotalRevenue());
        assertEquals(BigDecimal.valueOf(80), dto.getTotalExpense());
        assertEquals(BigDecimal.valueOf(120), dto.getNetProfit());
    }
}
