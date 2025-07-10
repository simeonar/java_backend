package com.sagedemo.backend.finance.entity;

import com.sagedemo.backend.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "finance_transaction")
public class Transaction extends BaseEntity {
    private LocalDateTime transactionDate;
    private String description;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    @ManyToOne
    private Account debitAccount;
    @ManyToOne
    private Account creditAccount;
    // Getters and Setters
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getDebitAmount() { return debitAmount; }
    public void setDebitAmount(BigDecimal debitAmount) { this.debitAmount = debitAmount; }
    public BigDecimal getCreditAmount() { return creditAmount; }
    public void setCreditAmount(BigDecimal creditAmount) { this.creditAmount = creditAmount; }
    public Account getDebitAccount() { return debitAccount; }
    public void setDebitAccount(Account debitAccount) { this.debitAccount = debitAccount; }
    public Account getCreditAccount() { return creditAccount; }
    public void setCreditAccount(Account creditAccount) { this.creditAccount = creditAccount; }
}
