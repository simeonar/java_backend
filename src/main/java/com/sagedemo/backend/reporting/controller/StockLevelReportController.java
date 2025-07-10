package com.sagedemo.backend.reporting.controller;

import com.sagedemo.backend.reporting.dto.StockLevelReportDTO;
import com.sagedemo.backend.reporting.service.StockLevelReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory/reports")
public class StockLevelReportController {
    private final StockLevelReportService stockLevelReportService;
    public StockLevelReportController(StockLevelReportService stockLevelReportService) {
        this.stockLevelReportService = stockLevelReportService;
    }
    @GetMapping("/stock-levels")
    public StockLevelReportDTO getStockLevelReport() {
        return stockLevelReportService.getStockLevelReport();
    }
}
