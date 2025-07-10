package com.sagedemo.backend.crm.controller;

import com.sagedemo.backend.crm.entity.Activity;
import com.sagedemo.backend.crm.service.ActivityService;
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

@WebMvcTest(ActivityController.class)
class ActivityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ActivityService activityService;

    @Test
    @WithMockUser
    void getAll_success() throws Exception {
        Activity activity = new Activity();
        activity.setId(1L);
        Mockito.when(activityService.findAll()).thenReturn(List.of(activity));
        mockMvc.perform(get("/api/crm/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L));
    }

    @Test
    @WithMockUser
    void getById_found() throws Exception {
        Activity activity = new Activity();
        activity.setId(1L);
        Mockito.when(activityService.findById(1L)).thenReturn(Optional.of(activity));
        mockMvc.perform(get("/api/crm/activities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    @WithMockUser
    void getById_notFound() throws Exception {
        Mockito.when(activityService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/crm/activities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }

    @Test
    @WithMockUser
    void create_success() throws Exception {
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setType("Call");
        activity.setActivityDate(java.time.LocalDateTime.now());
        Mockito.when(activityService.save(any())).thenReturn(activity);
        String json = "{" +
                "\"type\":\"Call\"," +
                "\"activityDate\":\"2025-07-10T12:00:00\"" +
                "}";
        mockMvc.perform(post("/api/crm/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    @WithMockUser
    void update_found() throws Exception {
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setType("Meeting");
        activity.setActivityDate(java.time.LocalDateTime.now());
        Mockito.when(activityService.update(eq(1L), any())).thenReturn(Optional.of(activity));
        String json = "{" +
                "\"type\":\"Meeting\"," +
                "\"activityDate\":\"2025-07-10T12:00:00\"" +
                "}";
        mockMvc.perform(put("/api/crm/activities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.type").value("Meeting"));
    }

    @Test
    @WithMockUser
    void update_notFound() throws Exception {
        Mockito.when(activityService.update(eq(1L), any())).thenReturn(Optional.empty());
        String json = "{" +
                "\"type\":\"Meeting\"," +
                "\"activityDate\":\"2025-07-10T12:00:00\"" +
                "}";
        mockMvc.perform(put("/api/crm/activities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }

    @Test
    @WithMockUser
    void delete_found() throws Exception {
        Mockito.when(activityService.delete(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/crm/activities/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @WithMockUser
    void delete_notFound() throws Exception {
        Mockito.when(activityService.delete(1L)).thenReturn(false);
        mockMvc.perform(delete("/api/crm/activities/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }
}
