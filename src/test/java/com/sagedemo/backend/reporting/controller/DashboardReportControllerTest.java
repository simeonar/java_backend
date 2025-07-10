package com.sagedemo.backend.reporting.controller;

import com.sagedemo.backend.reporting.dto.DashboardReportDTO;
import com.sagedemo.backend.reporting.service.DashboardReportService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DashboardReportController.class)
class DashboardReportControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DashboardReportService dashboardReportService;

    @Test
    @WithMockUser
    void getDashboardReport_success() throws Exception {
        DashboardReportDTO dto = new DashboardReportDTO();
        dto.setTotalSales(BigDecimal.valueOf(100));
        dto.setTotalPurchases(BigDecimal.valueOf(50));
        dto.setTotalProducts(5);
        dto.setTotalCustomers(3);
        dto.setTotalSuppliers(2);
        when(dashboardReportService.getDashboardReport()).thenReturn(dto);
        mockMvc.perform(get("/api/reporting/dashboard")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalSales").value(100))
                .andExpect(jsonPath("$.totalPurchases").value(50))
                .andExpect(jsonPath("$.totalProducts").value(5))
                .andExpect(jsonPath("$.totalCustomers").value(3))
                .andExpect(jsonPath("$.totalSuppliers").value(2));
    }
}
