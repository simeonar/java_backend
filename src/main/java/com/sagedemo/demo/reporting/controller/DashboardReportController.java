package com.sagedemo.demo.reporting.controller;

import com.sagedemo.demo.reporting.dto.DashboardReportDTO;
import com.sagedemo.demo.reporting.service.DashboardReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reporting")
public class DashboardReportController {
    private final DashboardReportService dashboardReportService;
    public DashboardReportController(DashboardReportService dashboardReportService) {
        this.dashboardReportService = dashboardReportService;
    }
    @GetMapping("/dashboard")
    public DashboardReportDTO getDashboardReport() {
        return dashboardReportService.getDashboardReport();
    }
}
