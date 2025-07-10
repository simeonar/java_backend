package com.sagedemo.backend.finance.service;

import com.sagedemo.backend.finance.dto.TransactionDTO;
import com.sagedemo.backend.finance.entity.Transaction;
import com.sagedemo.backend.finance.repository.TransactionRepository;
import com.sagedemo.backend.finance.repository.AccountRepository;
import com.sagedemo.backend.finance.entity.Account;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

        public Transaction createTransaction(TransactionDTO dto) {
        Account debit = accountRepository.findById(dto.getDebitAccountId())
                .orElseThrow(() -> new RuntimeException("Debit account not found"));
        Account credit = accountRepository.findById(dto.getCreditAccountId())
                .orElseThrow(() -> new RuntimeException("Credit account not found"));
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setDescription(dto.getDescription());
        transaction.setDebitAmount(dto.getAmount());
        transaction.setCreditAmount(dto.getAmount());
        transaction.setDebitAccount(debit);
        transaction.setCreditAccount(credit);
        return createTransaction(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        // Double-entry bookkeeping logic
        if (transaction.getDebitAccount() == null || transaction.getCreditAccount() == null) {
            throw new IllegalArgumentException("Both debit and credit accounts must be specified");
        }
        BigDecimal amount = transaction.getDebitAmount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        // Update debit account (increase balance)
        Account debit = accountRepository.findById(transaction.getDebitAccount().getId())
                .orElseThrow(() -> new RuntimeException("Debit account not found"));
        debit.setBalance(debit.getBalance().add(amount));
        accountRepository.save(debit);
        // Update credit account (decrease balance)
        Account credit = accountRepository.findById(transaction.getCreditAccount().getId())
                .orElseThrow(() -> new RuntimeException("Credit account not found"));
        credit.setBalance(credit.getBalance().subtract(amount));
        accountRepository.save(credit);
        // Set creditAmount for record
        transaction.setCreditAmount(amount);
        // Save transaction
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
