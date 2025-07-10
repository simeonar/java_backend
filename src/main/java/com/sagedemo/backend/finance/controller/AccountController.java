package com.sagedemo.backend.finance.controller;

import com.sagedemo.backend.finance.dto.AccountDTO;
import com.sagedemo.backend.finance.entity.Account;
import com.sagedemo.backend.finance.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/finance/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountDTO> getAllAccounts() {
        return accountService.getAllAccounts().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountDTO getAccountById(@PathVariable Long id) {
        Optional<Account> account = accountService.getAccountById(id);
        return account.map(this::toDTO).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @PostMapping
    public AccountDTO createAccount(@RequestBody AccountDTO accountDTO) {
        Account account = toEntity(accountDTO);
        return toDTO(accountService.createAccount(account));
    }

    @PutMapping("/{id}")
    public AccountDTO updateAccount(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        Account updated = toEntity(accountDTO);
        return toDTO(accountService.updateAccount(id, updated));
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

    private AccountDTO toDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountName(account.getAccountName());
        dto.setType(account.getType());
        dto.setBalance(account.getBalance());
        dto.setActive(account.isActive());
        return dto;
    }

    private Account toEntity(AccountDTO dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setAccountNumber(dto.getAccountNumber());
        account.setAccountName(dto.getAccountName());
        account.setType(dto.getType());
        account.setBalance(dto.getBalance());
        account.setActive(dto.isActive());
        return account;
    }
}
