package com.sagedemo.backend.production.controller;

import com.sagedemo.backend.production.entity.WorkCenter;
import com.sagedemo.backend.production.service.WorkCenterService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(WorkCenterController.class)
class WorkCenterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WorkCenterService workCenterService;

    @Test
    @WithMockUser
    void getAllWorkCenters_success() throws Exception {
        WorkCenter wc = new WorkCenter();
        wc.setId(1L);
        Mockito.when(workCenterService.getAllWorkCenters()).thenReturn(List.of(wc));
        mockMvc.perform(get("/api/production/work-centers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @WithMockUser
    void createWorkCenter_success() throws Exception {
        WorkCenter wc = new WorkCenter();
        wc.setId(2L);
        Mockito.when(workCenterService.createWorkCenter(any(WorkCenter.class))).thenReturn(wc);
        mockMvc.perform(post("/api/production/work-centers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"WC1\",\"description\":\"desc\"}")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L));
    }

    @Test
    @WithMockUser
    void updateWorkCenter_success() throws Exception {
        WorkCenter wc = new WorkCenter();
        wc.setId(3L);
        Mockito.when(workCenterService.updateWorkCenter(eq(3L), any(WorkCenter.class))).thenReturn(wc);
        mockMvc.perform(put("/api/production/work-centers/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"WC2\",\"description\":\"desc2\"}")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L));
    }

    @Test
    @WithMockUser
    void deleteWorkCenter_success() throws Exception {
        Mockito.doNothing().when(workCenterService).deleteWorkCenter(4L);
        mockMvc.perform(delete("/api/production/work-centers/4").with(csrf()))
                .andExpect(status().isOk());
    }
}
