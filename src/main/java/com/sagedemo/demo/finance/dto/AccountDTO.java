package com.sagedemo.demo.finance.dto;

import com.sagedemo.demo.finance.entity.AccountType;
import java.math.BigDecimal;

public class AccountDTO {
    private Long id;
    private String accountNumber;
    private String accountName;
    private AccountType type;
    private BigDecimal balance;
    private boolean active;
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
