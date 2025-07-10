package com.sagedemo.backend.sales.dto;

import com.sagedemo.backend.sales.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SalesOrderDTO {
    private Long id;
    private String orderNumber;
    private Long customerId;
    private LocalDate orderDate;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private List<SalesOrderItemDTO> items;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<SalesOrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<SalesOrderItemDTO> items) {
        this.items = items;
    }
}
