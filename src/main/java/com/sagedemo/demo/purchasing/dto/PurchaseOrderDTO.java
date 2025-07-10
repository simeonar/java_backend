package com.sagedemo.demo.purchasing.dto;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseOrderDTO {
    private Long id;
    private String orderNumber;
    private Long supplierId;
    private String supplierName;
    private String orderDate;
    private BigDecimal totalAmount;
    private List<PurchaseOrderItemDTO> items;
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public List<PurchaseOrderItemDTO> getItems() { return items; }
    public void setItems(List<PurchaseOrderItemDTO> items) { this.items = items; }
}
