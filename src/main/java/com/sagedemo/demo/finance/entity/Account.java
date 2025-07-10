package com.sagedemo.demo.finance.entity;

import com.sagedemo.demo.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "finance_account")
public class Account extends BaseEntity {
    private String accountNumber;
    private String accountName;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    private BigDecimal balance;
    private boolean active;
    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }
    public AccountType getType() { return type; }
    public void setType(AccountType type) { this.type = type; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}

