package com.sagedemo.backend.crm.controller;

import com.sagedemo.backend.crm.entity.Lead;
import com.sagedemo.backend.crm.service.LeadService;
import com.sagedemo.backend.common.api.ApiResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(LeadController.class)
class LeadControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LeadService leadService;

    @Test
    @WithMockUser
    void getAll_success() throws Exception {
        Lead lead = new Lead();
        lead.setId(1L);
        Mockito.when(leadService.findAll()).thenReturn(List.of(lead));
        mockMvc.perform(get("/api/crm/leads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L));
    }

    @Test
    @WithMockUser
    void getById_found() throws Exception {
        Lead lead = new Lead();
        lead.setId(2L);
        Mockito.when(leadService.findById(2L)).thenReturn(Optional.of(lead));
        mockMvc.perform(get("/api/crm/leads/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(2L));
    }

    @Test
    @WithMockUser
    void getById_notFound() throws Exception {
        Mockito.when(leadService.findById(3L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/crm/leads/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }

    @Test
    @WithMockUser
    void create_success() throws Exception {
        Lead lead = new Lead();
        lead.setId(4L);
        Mockito.when(leadService.save(any(Lead.class))).thenReturn(lead);
        mockMvc.perform(post("/api/crm/leads")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Lead\",\"email\":\"test@example.com\",\"status\":\"NEW\"}")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(4L));
    }

    @Test
    @WithMockUser
    void update_found() throws Exception {
        Lead lead = new Lead();
        lead.setId(5L);
        Mockito.when(leadService.update(eq(5L), any(Lead.class))).thenReturn(Optional.of(lead));
        mockMvc.perform(put("/api/crm/leads/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Lead\",\"email\":\"updated@example.com\",\"status\":\"QUALIFIED\"}")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(5L));
    }

    @Test
    @WithMockUser
    void update_notFound() throws Exception {
        Mockito.when(leadService.update(eq(6L), any(Lead.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/crm/leads/6")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Lead\",\"email\":\"updated@example.com\",\"status\":\"QUALIFIED\"}")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }

    @Test
    @WithMockUser
    void delete_found() throws Exception {
        Mockito.when(leadService.delete(7L)).thenReturn(true);
        mockMvc.perform(delete("/api/crm/leads/7").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @WithMockUser
    void delete_notFound() throws Exception {
        Mockito.when(leadService.delete(8L)).thenReturn(false);
        mockMvc.perform(delete("/api/crm/leads/8").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }
}
