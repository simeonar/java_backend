package com.sagedemo.backend.production.service;

import com.sagedemo.backend.production.entity.WorkCenter;
import com.sagedemo.backend.production.repository.WorkCenterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkCenterServiceTest {
    @Mock
    private WorkCenterRepository workCenterRepository;
    @InjectMocks
    private WorkCenterService workCenterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createWorkCenter_success() {
        WorkCenter wc = new WorkCenter();
        when(workCenterRepository.save(wc)).thenReturn(wc);
        WorkCenter result = workCenterService.createWorkCenter(wc);
        assertNotNull(result);
    }

    @Test
    void getAllWorkCenters_returnsList() {
        WorkCenter wc = new WorkCenter();
        when(workCenterRepository.findAll()).thenReturn(List.of(wc));
        List<WorkCenter> result = workCenterService.getAllWorkCenters();
        assertEquals(1, result.size());
    }

    @Test
    void updateWorkCenter_found() {
        WorkCenter existing = new WorkCenter();
        existing.setName("Old");
        WorkCenter updated = new WorkCenter();
        updated.setName("New");
        updated.setDescription("desc");
        when(workCenterRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(workCenterRepository.save(any(WorkCenter.class))).thenReturn(updated);
        WorkCenter result = workCenterService.updateWorkCenter(1L, updated);
        assertEquals("New", result.getName());
        assertEquals("desc", result.getDescription());
    }

    @Test
    void updateWorkCenter_notFound() {
        WorkCenter updated = new WorkCenter();
        when(workCenterRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> workCenterService.updateWorkCenter(2L, updated));
    }

    @Test
    void deleteWorkCenter_success() {
        doNothing().when(workCenterRepository).deleteById(1L);
        assertDoesNotThrow(() -> workCenterService.deleteWorkCenter(1L));
    }
}
