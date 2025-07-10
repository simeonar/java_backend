package com.sagedemo.backend.finance.entity;

import com.sagedemo.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "finance_journal")
public class Journal extends BaseEntity {
    private LocalDateTime entryDate;
    private String description;
    @ManyToOne
    private Transaction transaction;
    private String createdBy;
    // Getters and Setters
    public LocalDateTime getEntryDate() { return entryDate; }
    public void setEntryDate(LocalDateTime entryDate) { this.entryDate = entryDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Transaction getTransaction() { return transaction; }
    public void setTransaction(Transaction transaction) { this.transaction = transaction; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}
