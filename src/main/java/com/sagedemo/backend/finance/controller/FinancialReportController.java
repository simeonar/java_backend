package com.sagedemo.backend.finance.controller;

import com.sagedemo.backend.finance.dto.BalanceReportDTO;
import com.sagedemo.backend.finance.dto.ProfitLossReportDTO;
import com.sagedemo.backend.finance.service.FinancialReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/finance/reports")
public class FinancialReportController {
    private final FinancialReportService financialReportService;

    public FinancialReportController(FinancialReportService financialReportService) {
        this.financialReportService = financialReportService;
    }

    @GetMapping("/balance")
    public BalanceReportDTO getBalanceReport() {
        return financialReportService.getBalanceReport();
    }

    @GetMapping("/profit-loss")
    public ProfitLossReportDTO getProfitLossReport() {
        return financialReportService.getProfitLossReport();
    }
}
