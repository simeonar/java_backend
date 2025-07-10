package com.sagedemo.backend.finance.controller;

import com.sagedemo.backend.finance.service.JournalService;
import com.sagedemo.backend.finance.entity.Journal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JournalController.class)
class JournalControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JournalService journalService;
    @Test
    @WithMockUser
    void testGetAllEntriesReturnsEmptyList() throws Exception {
        when(journalService.getAllEntries()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/finance/journal"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
