package com.sagedemo.backend.reporting.service;

import com.sagedemo.backend.reporting.dto.StockLevelReportDTO;
import org.springframework.stereotype.Service;

@Service
public class CsvExportService {
    public String exportStockLevelToCsv(StockLevelReportDTO report) {
        StringBuilder sb = new StringBuilder();
        sb.append("Product ID,Product Name,Warehouse,Quantity\n");
        for (StockLevelReportDTO.StockLevelItem item : report.getItems()) {
            sb.append(item.getProductId()).append(",")
              .append(escape(item.getProductName())).append(",")
              .append(escape(item.getWarehouseName())).append(",")
              .append(item.getQuantity()).append("\n");
        }
        return sb.toString();
    }
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "\"\"");
    }
}
