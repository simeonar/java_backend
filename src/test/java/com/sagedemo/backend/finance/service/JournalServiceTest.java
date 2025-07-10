package com.sagedemo.backend.finance.service;

import com.sagedemo.backend.finance.repository.JournalRepository;
import com.sagedemo.backend.finance.entity.Journal;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JournalServiceTest {
    @Mock
    private JournalRepository journalRepository;
    @InjectMocks
    private JournalService journalService;
    public JournalServiceTest() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetAllEntriesReturnsEmptyList() {
        when(journalRepository.findAll()).thenReturn(Collections.emptyList());
        List<Journal> result = journalService.getAllEntries();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
