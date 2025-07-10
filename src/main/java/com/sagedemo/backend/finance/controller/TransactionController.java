package com.sagedemo.backend.finance.controller;

import com.sagedemo.backend.finance.dto.TransactionDTO;
import com.sagedemo.backend.finance.entity.Transaction;
import com.sagedemo.backend.finance.entity.Account;
import com.sagedemo.backend.finance.service.TransactionService;
import com.sagedemo.backend.finance.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/finance/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        return transaction.orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody TransactionDTO dto) {
        // Map DTO to entity
        Account debit = accountService.getAccountById(dto.getDebitAccountId())
                .orElseThrow(() -> new RuntimeException("Debit account not found"));
        Account credit = accountService.getAccountById(dto.getCreditAccountId())
                .orElseThrow(() -> new RuntimeException("Credit account not found"));
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setDescription(dto.getDescription());
        transaction.setDebitAmount(dto.getAmount());
        transaction.setCreditAmount(dto.getAmount());
        transaction.setDebitAccount(debit);
        transaction.setCreditAccount(credit);
        return transactionService.createTransaction(transaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
