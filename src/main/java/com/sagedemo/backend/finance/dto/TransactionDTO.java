package com.sagedemo.backend.finance.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private LocalDateTime transactionDate;
    private String description;
    private BigDecimal amount;
    private Long debitAccountId;
    private Long creditAccountId;
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public Long getDebitAccountId() { return debitAccountId; }
    public void setDebitAccountId(Long debitAccountId) { this.debitAccountId = debitAccountId; }
    public Long getCreditAccountId() { return creditAccountId; }
    public void setCreditAccountId(Long creditAccountId) { this.creditAccountId = creditAccountId; }
}
