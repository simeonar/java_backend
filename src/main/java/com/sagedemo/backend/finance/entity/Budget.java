package com.sagedemo.backend.finance.entity;

import com.sagedemo.backend.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "finance_budget")
public class Budget extends BaseEntity {
    private String name;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private BudgetStatus status;
    private String period; // e.g. "2025-Q3" or "2025"
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public BudgetStatus getStatus() { return status; }
    public void setStatus(BudgetStatus status) { this.status = status; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}
