package com.sagedemo.demo.reporting.service;

import com.sagedemo.demo.reporting.dto.StockLevelReportDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

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
