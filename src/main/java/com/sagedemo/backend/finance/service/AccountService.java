package com.sagedemo.backend.finance.service;

import com.sagedemo.backend.finance.entity.Account;
import com.sagedemo.backend.finance.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account updatedAccount) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setAccountNumber(updatedAccount.getAccountNumber());
                    account.setAccountName(updatedAccount.getAccountName());
                    account.setType(updatedAccount.getType());
                    account.setBalance(updatedAccount.getBalance());
                    account.setActive(updatedAccount.isActive());
                    return accountRepository.save(account);
                })
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
