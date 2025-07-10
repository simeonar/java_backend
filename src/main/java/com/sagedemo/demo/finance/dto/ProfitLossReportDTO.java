package com.sagedemo.demo.finance.dto;

import java.math.BigDecimal;
import java.util.Map;

public class ProfitLossReportDTO {
    private Map<String, BigDecimal> revenues;
    private Map<String, BigDecimal> expenses;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpense;
    private BigDecimal netProfit;

    public Map<String, BigDecimal> getRevenues() { return revenues; }
    public void setRevenues(Map<String, BigDecimal> revenues) { this.revenues = revenues; }
    public Map<String, BigDecimal> getExpenses() { return expenses; }
    public void setExpenses(Map<String, BigDecimal> expenses) { this.expenses = expenses; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
    public BigDecimal getTotalExpense() { return totalExpense; }
    public void setTotalExpense(BigDecimal totalExpense) { this.totalExpense = totalExpense; }
    public BigDecimal getNetProfit() { return netProfit; }
    public void setNetProfit(BigDecimal netProfit) { this.netProfit = netProfit; }
}
