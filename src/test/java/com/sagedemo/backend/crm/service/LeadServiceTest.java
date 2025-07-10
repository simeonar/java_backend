package com.sagedemo.backend.crm.service;

import com.sagedemo.backend.crm.entity.Lead;
import com.sagedemo.backend.crm.repository.LeadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeadServiceTest {
    @Mock
    private LeadRepository leadRepository;
    @InjectMocks
    private LeadService leadService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_returnsList() {
        Lead lead = new Lead();
        when(leadRepository.findAll()).thenReturn(List.of(lead));
        List<Lead> result = leadService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void findById_found() {
        Lead lead = new Lead();
        when(leadRepository.findById(1L)).thenReturn(Optional.of(lead));
        Optional<Lead> result = leadService.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void findById_notFound() {
        when(leadRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Lead> result = leadService.findById(2L);
        assertTrue(result.isEmpty());
    }

    @Test
    void save_success() {
        Lead lead = new Lead();
        when(leadRepository.save(lead)).thenReturn(lead);
        Lead result = leadService.save(lead);
        assertNotNull(result);
    }

    @Test
    void update_found() {
        Lead existing = new Lead();
        existing.setName("Old");
        Lead updated = new Lead();
        updated.setName("New");
        when(leadRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(leadRepository.save(any(Lead.class))).thenReturn(updated);
        Optional<Lead> result = leadService.update(1L, updated);
        assertTrue(result.isPresent());
        assertEquals("New", result.get().getName());
    }

    @Test
    void update_notFound() {
        Lead updated = new Lead();
        when(leadRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Lead> result = leadService.update(2L, updated);
        assertTrue(result.isEmpty());
    }

    @Test
    void delete_found() {
        when(leadRepository.existsById(1L)).thenReturn(true);
        doNothing().when(leadRepository).deleteById(1L);
        boolean result = leadService.delete(1L);
        assertTrue(result);
    }

    @Test
    void delete_notFound() {
        when(leadRepository.existsById(2L)).thenReturn(false);
        boolean result = leadService.delete(2L);
        assertFalse(result);
    }
}
