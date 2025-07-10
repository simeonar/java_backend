package com.sagedemo.demo.reporting.dto;

import java.util.List;

public class StockLevelReportDTO {
    private List<StockLevelItem> items;
    public List<StockLevelItem> getItems() { return items; }
    public void setItems(List<StockLevelItem> items) { this.items = items; }
    public static class StockLevelItem {
        private Long productId;
        private String productName;
        private String warehouseName;
        private Integer quantity;
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public String getWarehouseName() { return warehouseName; }
        public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}
