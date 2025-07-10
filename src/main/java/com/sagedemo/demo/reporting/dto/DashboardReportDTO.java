package com.sagedemo.demo.reporting.dto;

import java.math.BigDecimal;

public class DashboardReportDTO {
    private BigDecimal totalSales;
    private BigDecimal totalPurchases;
    private Integer totalProducts;
    private Integer totalCustomers;
    private Integer totalSuppliers;
    public BigDecimal getTotalSales() { return totalSales; }
    public void setTotalSales(BigDecimal totalSales) { this.totalSales = totalSales; }
    public BigDecimal getTotalPurchases() { return totalPurchases; }
    public void setTotalPurchases(BigDecimal totalPurchases) { this.totalPurchases = totalPurchases; }
    public Integer getTotalProducts() { return totalProducts; }
    public void setTotalProducts(Integer totalProducts) { this.totalProducts = totalProducts; }
    public Integer getTotalCustomers() { return totalCustomers; }
    public void setTotalCustomers(Integer totalCustomers) { this.totalCustomers = totalCustomers; }
    public Integer getTotalSuppliers() { return totalSuppliers; }
    public void setTotalSuppliers(Integer totalSuppliers) { this.totalSuppliers = totalSuppliers; }
}
