package com.sagedemo.backend.crm.service;

import com.sagedemo.backend.crm.entity.Opportunity;
import com.sagedemo.backend.crm.repository.OpportunityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OpportunityServiceTest {
    @Mock
    private OpportunityRepository opportunityRepository;
    @InjectMocks
    private OpportunityService opportunityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_returnsList() {
        Opportunity opportunity = new Opportunity();
        when(opportunityRepository.findAll()).thenReturn(List.of(opportunity));
        List<Opportunity> result = opportunityService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void findById_found() {
        Opportunity opportunity = new Opportunity();
        when(opportunityRepository.findById(1L)).thenReturn(Optional.of(opportunity));
        Optional<Opportunity> result = opportunityService.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void save_returnsSaved() {
        Opportunity opportunity = new Opportunity();
        when(opportunityRepository.save(opportunity)).thenReturn(opportunity);
        Opportunity result = opportunityService.save(opportunity);
        assertNotNull(result);
    }

    @Test
    void update_found() {
        Opportunity existing = new Opportunity();
        existing.setName("Old");
        Opportunity updated = new Opportunity();
        updated.setName("New");
        when(opportunityRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(opportunityRepository.save(any())).thenReturn(updated);
        Optional<Opportunity> result = opportunityService.update(1L, updated);
        assertTrue(result.isPresent());
        assertEquals("New", result.get().getName());
    }

    @Test
    void delete_found() {
        when(opportunityRepository.existsById(1L)).thenReturn(true);
        doNothing().when(opportunityRepository).deleteById(1L);
        boolean result = opportunityService.delete(1L);
        assertTrue(result);
    }

    @Test
    void delete_notFound() {
        when(opportunityRepository.existsById(1L)).thenReturn(false);
        boolean result = opportunityService.delete(1L);
        assertFalse(result);
    }
}
