package com.sagedemo.demo.production.entity;

import com.sagedemo.demo.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "production_order")
public class ProductionOrder extends BaseEntity {
    private String orderNumber;
    private LocalDate orderDate;
    @Enumerated(EnumType.STRING)
    private ProductionOrderStatus status;
    @ManyToOne
    private BillOfMaterials billOfMaterials;
    private Integer quantity;
    // Getters and Setters
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public ProductionOrderStatus getStatus() { return status; }
    public void setStatus(ProductionOrderStatus status) { this.status = status; }
    public BillOfMaterials getBillOfMaterials() { return billOfMaterials; }
    public void setBillOfMaterials(BillOfMaterials billOfMaterials) { this.billOfMaterials = billOfMaterials; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
