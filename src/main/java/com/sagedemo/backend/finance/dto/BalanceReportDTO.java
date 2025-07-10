package com.sagedemo.backend.finance.dto;

import java.math.BigDecimal;
import java.util.Map;

public class BalanceReportDTO {
    private Map<String, BigDecimal> assets;
    private Map<String, BigDecimal> liabilities;
    private Map<String, BigDecimal> equity;
    private BigDecimal totalAssets;
    private BigDecimal totalLiabilities;
    private BigDecimal totalEquity;

    public Map<String, BigDecimal> getAssets() { return assets; }
    public void setAssets(Map<String, BigDecimal> assets) { this.assets = assets; }
    public Map<String, BigDecimal> getLiabilities() { return liabilities; }
    public void setLiabilities(Map<String, BigDecimal> liabilities) { this.liabilities = liabilities; }
    public Map<String, BigDecimal> getEquity() { return equity; }
    public void setEquity(Map<String, BigDecimal> equity) { this.equity = equity; }
    public BigDecimal getTotalAssets() { return totalAssets; }
    public void setTotalAssets(BigDecimal totalAssets) { this.totalAssets = totalAssets; }
    public BigDecimal getTotalLiabilities() { return totalLiabilities; }
    public void setTotalLiabilities(BigDecimal totalLiabilities) { this.totalLiabilities = totalLiabilities; }
    public BigDecimal getTotalEquity() { return totalEquity; }
    public void setTotalEquity(BigDecimal totalEquity) { this.totalEquity = totalEquity; }
}
