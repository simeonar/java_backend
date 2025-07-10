package com.sagedemo.backend.sales.config;

import com.sagedemo.backend.finance.entity.Account;
import com.sagedemo.backend.finance.entity.AccountType;
import com.sagedemo.backend.finance.repository.AccountRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestFinanceAccountConfig {
    @Bean
    @Primary
    @DependsOn("accountRepository")
    public boolean createTestFinanceAccounts(AccountRepository accountRepository) {
        if (!accountRepository.existsById(1L)) {
            Account bank = new Account();
            bank.setId(1L);
            bank.setAccountNumber("BANK-001");
            bank.setAccountName("Test Bank Account");
            bank.setType(AccountType.ASSET);
            bank.setBalance(java.math.BigDecimal.valueOf(10000));
            accountRepository.save(bank);
        }
        if (!accountRepository.existsById(2L)) {
            Account sales = new Account();
            sales.setId(2L);
            sales.setAccountNumber("SALES-001");
            sales.setAccountName("Test Sales Revenue");
            sales.setType(AccountType.REVENUE);
            sales.setBalance(java.math.BigDecimal.ZERO);
            accountRepository.save(sales);
        }
        return true;
    }
}
