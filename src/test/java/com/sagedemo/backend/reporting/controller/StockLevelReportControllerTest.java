package com.sagedemo.backend.reporting.controller;

import com.sagedemo.backend.reporting.dto.StockLevelReportDTO;
import com.sagedemo.backend.reporting.service.StockLevelReportService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockLevelReportController.class)
class StockLevelReportControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StockLevelReportService stockLevelReportService;

    @Test
    @WithMockUser
    void getStockLevelReport_success() throws Exception {
        StockLevelReportDTO.StockLevelItem item = new StockLevelReportDTO.StockLevelItem();
        item.setProductId(1L);
        item.setProductName("TestProduct");
        item.setWarehouseName("MainWH");
        item.setQuantity(42);
        StockLevelReportDTO dto = new StockLevelReportDTO();
        dto.setItems(List.of(item));
        when(stockLevelReportService.getStockLevelReport()).thenReturn(dto);
        mockMvc.perform(get("/api/inventory/reports/stock-levels")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].productName").value("TestProduct"))
                .andExpect(jsonPath("$.items[0].warehouseName").value("MainWH"))
                .andExpect(jsonPath("$.items[0].quantity").value(42));
    }
}
