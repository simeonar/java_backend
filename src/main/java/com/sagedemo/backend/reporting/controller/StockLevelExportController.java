package com.sagedemo.backend.reporting.controller;

import com.sagedemo.backend.reporting.dto.StockLevelReportDTO;
import com.sagedemo.backend.reporting.service.StockLevelReportService;
import com.sagedemo.backend.reporting.service.CsvExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory/reports")
public class StockLevelExportController {
    private final StockLevelReportService stockLevelReportService;
    private final CsvExportService csvExportService;
    public StockLevelExportController(StockLevelReportService stockLevelReportService, CsvExportService csvExportService) {
        this.stockLevelReportService = stockLevelReportService;
        this.csvExportService = csvExportService;
    }
    @GetMapping("/stock-levels/export/csv")
    public ResponseEntity<String> exportStockLevelCsv() {
        StockLevelReportDTO report = stockLevelReportService.getStockLevelReport();
        String csv = csvExportService.exportStockLevelToCsv(report);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stock_levels.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv);
    }
}
