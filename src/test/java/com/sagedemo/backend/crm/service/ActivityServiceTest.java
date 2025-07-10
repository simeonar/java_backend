package com.sagedemo.backend.crm.service;

import com.sagedemo.backend.crm.entity.Activity;
import com.sagedemo.backend.crm.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityServiceTest {
    @Mock
    private ActivityRepository activityRepository;
    @InjectMocks
    private ActivityService activityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_returnsList() {
        Activity activity = new Activity();
        when(activityRepository.findAll()).thenReturn(List.of(activity));
        List<Activity> result = activityService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void findById_found() {
        Activity activity = new Activity();
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        Optional<Activity> result = activityService.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void save_returnsSaved() {
        Activity activity = new Activity();
        when(activityRepository.save(activity)).thenReturn(activity);
        Activity result = activityService.save(activity);
        assertNotNull(result);
    }

    @Test
    void update_found() {
        Activity existing = new Activity();
        existing.setType("Call");
        Activity updated = new Activity();
        updated.setType("Meeting");
        when(activityRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(activityRepository.save(any())).thenReturn(updated);
        Optional<Activity> result = activityService.update(1L, updated);
        assertTrue(result.isPresent());
        assertEquals("Meeting", result.get().getType());
    }

    @Test
    void delete_found() {
        when(activityRepository.existsById(1L)).thenReturn(true);
        doNothing().when(activityRepository).deleteById(1L);
        boolean result = activityService.delete(1L);
        assertTrue(result);
    }

    @Test
    void delete_notFound() {
        when(activityRepository.existsById(1L)).thenReturn(false);
        boolean result = activityService.delete(1L);
        assertFalse(result);
    }
}
