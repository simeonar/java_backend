package com.sagedemo.backend.production.controller;

import com.sagedemo.backend.production.entity.BillOfMaterials;
import com.sagedemo.backend.production.service.BillOfMaterialsService;
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

@WebMvcTest(BillOfMaterialsController.class)
class BillOfMaterialsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BillOfMaterialsService billOfMaterialsService;

    @Test
    @WithMockUser
    void getAll_success() throws Exception {
        BillOfMaterials bom = new BillOfMaterials();
        bom.setId(1L);
        Mockito.when(billOfMaterialsService.findAll()).thenReturn(List.of(bom));
        mockMvc.perform(get("/api/bom"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @WithMockUser
    void getById_found() throws Exception {
        BillOfMaterials bom = new BillOfMaterials();
        bom.setId(2L);
        Mockito.when(billOfMaterialsService.findById(2L)).thenReturn(Optional.of(bom));
        mockMvc.perform(get("/api/bom/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L));
    }

    @Test
    @WithMockUser
    void getById_notFound() throws Exception {
        Mockito.when(billOfMaterialsService.findById(3L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/bom/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void create_success() throws Exception {
        BillOfMaterials bom = new BillOfMaterials();
        bom.setId(4L);
        Mockito.when(billOfMaterialsService.save(any(BillOfMaterials.class))).thenReturn(bom);
        mockMvc.perform(post("/api/bom")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test BOM\",\"components\":[\"A\",\"B\"]}")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4L));
    }

    @Test
    @WithMockUser
    void update_found() throws Exception {
        BillOfMaterials bom = new BillOfMaterials();
        bom.setId(5L);
        Mockito.when(billOfMaterialsService.update(eq(5L), any(BillOfMaterials.class))).thenReturn(Optional.of(bom));
        mockMvc.perform(put("/api/bom/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated BOM\",\"components\":[\"C\"]}")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L));
    }

    @Test
    @WithMockUser
    void update_notFound() throws Exception {
        Mockito.when(billOfMaterialsService.update(eq(6L), any(BillOfMaterials.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/bom/6")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated BOM\",\"components\":[\"C\"]}")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void delete_found() throws Exception {
        Mockito.when(billOfMaterialsService.delete(7L)).thenReturn(true);
        mockMvc.perform(delete("/api/bom/7").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void delete_notFound() throws Exception {
        Mockito.when(billOfMaterialsService.delete(8L)).thenReturn(false);
        mockMvc.perform(delete("/api/bom/8").with(csrf()))
                .andExpect(status().isNotFound());
    }
}
