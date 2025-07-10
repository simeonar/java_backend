package com.sagedemo.demo.purchasing.entity;

import com.sagedemo.demo.common.entity.BaseEntity;
import com.sagedemo.demo.inventory.entity.Product;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "purchasing_purchase_order_item")
public class PurchaseOrderItem extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;
    @ManyToOne(optional = false)
    private Product product;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    // Getters and Setters
    public PurchaseOrder getPurchaseOrder() { return purchaseOrder; }
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) { this.purchaseOrder = purchaseOrder; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}
