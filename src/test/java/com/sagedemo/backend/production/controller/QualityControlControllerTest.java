package com.sagedemo.backend.production.controller;

import com.sagedemo.backend.production.entity.QualityControl;
import com.sagedemo.backend.production.service.QualityControlService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(QualityControlController.class)
class QualityControlControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QualityControlService qualityControlService;

    @Test
    @WithMockUser
    void createQC_success() throws Exception {
        QualityControl qc = new QualityControl();
        qc.setId(1L);
        Mockito.when(qualityControlService.createQualityControl(any(QualityControl.class))).thenReturn(qc);
        mockMvc.perform(post("/api/production/quality-control")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productionOrderId\":10,\"inspectionDate\":\"2025-07-10\",\"inspector\":\"Ivan\",\"result\":\"PASS\",\"notes\":\"OK\"}")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser
    void getAllQC_success() throws Exception {
        QualityControl qc = new QualityControl();
        qc.setId(2L);
        Mockito.when(qualityControlService.getAllQualityControls()).thenReturn(List.of(qc));
        mockMvc.perform(get("/api/production/quality-control"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L));
    }
}
