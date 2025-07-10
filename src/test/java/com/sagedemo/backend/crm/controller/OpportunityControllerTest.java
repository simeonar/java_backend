package com.sagedemo.backend.crm.controller;

import com.sagedemo.backend.crm.entity.Opportunity;
import com.sagedemo.backend.crm.service.OpportunityService;
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

@WebMvcTest(OpportunityController.class)
class OpportunityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OpportunityService opportunityService;

    @Test
    @WithMockUser
    void getAll_success() throws Exception {
        Opportunity opportunity = new Opportunity();
        opportunity.setId(1L);
        Mockito.when(opportunityService.findAll()).thenReturn(List.of(opportunity));
        mockMvc.perform(get("/api/crm/opportunities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L));
    }

    @Test
    @WithMockUser
    void getById_found() throws Exception {
        Opportunity opportunity = new Opportunity();
        opportunity.setId(1L);
        Mockito.when(opportunityService.findById(1L)).thenReturn(Optional.of(opportunity));
        mockMvc.perform(get("/api/crm/opportunities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    @WithMockUser
    void getById_notFound() throws Exception {
        Mockito.when(opportunityService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/crm/opportunities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }

    @Test
    @WithMockUser
    void create_success() throws Exception {
        Opportunity opportunity = new Opportunity();
        opportunity.setId(1L);
        opportunity.setName("Test");
        opportunity.setStage("New");
        opportunity.setValue(100.0);
        Mockito.when(opportunityService.save(any())).thenReturn(opportunity);
        String json = "{" +
                "\"name\":\"Test\"," +
                "\"stage\":\"New\"," +
                "\"value\":100.0" +
                "}";
        mockMvc.perform(post("/api/crm/opportunities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    @WithMockUser
    void update_found() throws Exception {
        Opportunity opportunity = new Opportunity();
        opportunity.setId(1L);
        opportunity.setName("Updated");
        opportunity.setStage("Won");
        opportunity.setValue(200.0);
        Mockito.when(opportunityService.update(eq(1L), any())).thenReturn(Optional.of(opportunity));
        String json = "{" +
                "\"name\":\"Updated\"," +
                "\"stage\":\"Won\"," +
                "\"value\":200.0" +
                "}";
        mockMvc.perform(put("/api/crm/opportunities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated"));
    }

    @Test
    @WithMockUser
    void update_notFound() throws Exception {
        Mockito.when(opportunityService.update(eq(1L), any())).thenReturn(Optional.empty());
        String json = "{" +
                "\"name\":\"Updated\"," +
                "\"stage\":\"Won\"," +
                "\"value\":200.0" +
                "}";
        mockMvc.perform(put("/api/crm/opportunities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }

    @Test
    @WithMockUser
    void delete_found() throws Exception {
        Mockito.when(opportunityService.delete(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/crm/opportunities/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @WithMockUser
    void delete_notFound() throws Exception {
        Mockito.when(opportunityService.delete(1L)).thenReturn(false);
        mockMvc.perform(delete("/api/crm/opportunities/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }
}
