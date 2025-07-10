package com.sagedemo.demo.production.entity;

import com.sagedemo.demo.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "production_schedule")
public class ProductionSchedule extends BaseEntity {
    private LocalDate scheduledDate;
    private Long productionOrderId;
    private String workCenter;
    private String status;
    // Getters and Setters
    public LocalDate getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDate scheduledDate) { this.scheduledDate = scheduledDate; }
    public Long getProductionOrderId() { return productionOrderId; }
    public void setProductionOrderId(Long productionOrderId) { this.productionOrderId = productionOrderId; }
    public String getWorkCenter() { return workCenter; }
    public void setWorkCenter(String workCenter) { this.workCenter = workCenter; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
