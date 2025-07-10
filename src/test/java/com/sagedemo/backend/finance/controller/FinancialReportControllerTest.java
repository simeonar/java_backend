package com.sagedemo.backend.finance.controller;

import com.sagedemo.backend.finance.dto.BalanceReportDTO;
import com.sagedemo.backend.finance.dto.ProfitLossReportDTO;
import com.sagedemo.backend.finance.service.FinancialReportService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.Map;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FinancialReportController.class)
class FinancialReportControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FinancialReportService financialReportService;

    @Test
    @WithMockUser
    void getBalanceReport_success() throws Exception {
        BalanceReportDTO dto = new BalanceReportDTO();
        dto.setAssets(Map.of("Cash", BigDecimal.valueOf(100)));
        dto.setLiabilities(Map.of("Loan", BigDecimal.valueOf(50)));
        dto.setEquity(Map.of("Capital", BigDecimal.valueOf(30)));
        dto.setTotalAssets(BigDecimal.valueOf(100));
        dto.setTotalLiabilities(BigDecimal.valueOf(50));
        dto.setTotalEquity(BigDecimal.valueOf(30));
        when(financialReportService.getBalanceReport()).thenReturn(dto);
        mockMvc.perform(get("/api/finance/reports/balance")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAssets").value(100))
                .andExpect(jsonPath("$.totalLiabilities").value(50))
                .andExpect(jsonPath("$.totalEquity").value(30));
    }

    @Test
    @WithMockUser
    void getProfitLossReport_success() throws Exception {
        ProfitLossReportDTO dto = new ProfitLossReportDTO();
        dto.setRevenues(Map.of("Sales", BigDecimal.valueOf(200)));
        dto.setExpenses(Map.of("Rent", BigDecimal.valueOf(80)));
        dto.setTotalRevenue(BigDecimal.valueOf(200));
        dto.setTotalExpense(BigDecimal.valueOf(80));
        dto.setNetProfit(BigDecimal.valueOf(120));
        when(financialReportService.getProfitLossReport()).thenReturn(dto);
        mockMvc.perform(get("/api/finance/reports/profit-loss")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRevenue").value(200))
                .andExpect(jsonPath("$.totalExpense").value(80))
                .andExpect(jsonPath("$.netProfit").value(120));
    }
}
