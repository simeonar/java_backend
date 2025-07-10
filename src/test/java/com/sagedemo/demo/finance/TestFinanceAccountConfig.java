package com.sagedemo.demo.finance;

import com.sagedemo.demo.finance.entity.Account;
import com.sagedemo.demo.finance.entity.AccountType;
import com.sagedemo.demo.finance.repository.AccountRepository;
import com.sagedemo.demo.finance.util.FinanceAccountConstants;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestFinanceAccountConfig {
    @Bean
    public boolean createTestFinanceAccounts(AccountRepository accountRepository) {
        // BANK ACCOUNT
        Account bank = accountRepository.findAll().stream()
                .filter(a -> "BANK-001".equals(a.getAccountNumber()))
                .findFirst().orElse(null);
        if (bank == null) {
            bank = new Account();
            bank.setAccountName("Test Bank Account");
            bank.setAccountNumber("BANK-001");
            bank.setType(AccountType.ASSET);
            bank.setBalance(java.math.BigDecimal.valueOf(10000));
            bank.setActive(true);
            accountRepository.save(bank);
        }
        // SALES REVENUE ACCOUNT
        Account sales = accountRepository.findAll().stream()
                .filter(a -> "REV-001".equals(a.getAccountNumber()))
                .findFirst().orElse(null);
        if (sales == null) {
            sales = new Account();
            sales.setAccountName("Test Sales Revenue");
            sales.setAccountNumber("REV-001");
            sales.setType(AccountType.REVENUE);
            sales.setBalance(java.math.BigDecimal.ZERO);
            sales.setActive(true);
            accountRepository.save(sales);
        }
        return true;
    }
}
