package com.sagedemo.backend.inventory.service;

import com.sagedemo.backend.inventory.entity.StockMovement;
import com.sagedemo.backend.inventory.repository.StockMovementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockMovementServiceTest {
    @Mock
    private StockMovementRepository stockMovementRepository;
    @InjectMocks
    private StockMovementService stockMovementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMovements() {
        StockMovement m1 = new StockMovement();
        StockMovement m2 = new StockMovement();
        when(stockMovementRepository.findAll()).thenReturn(Arrays.asList(m1, m2));
        List<StockMovement> result = stockMovementService.getAllMovements();
        assertEquals(2, result.size());
    }

    @Test
    void testFindById() {
        StockMovement m = new StockMovement();
        when(stockMovementRepository.findById(1L)).thenReturn(Optional.of(m));
        Optional<StockMovement> result = stockMovementService.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testSave() {
        StockMovement m = new StockMovement();
        when(stockMovementRepository.save(any())).thenReturn(m);
        StockMovement saved = stockMovementService.save(m);
        assertNotNull(saved);
    }

    @Test
    void testUpdate() {
        StockMovement existing = new StockMovement();
        existing.setQuantity(5);
        StockMovement updated = new StockMovement();
        updated.setQuantity(10);
        when(stockMovementRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(stockMovementRepository.save(any())).thenReturn(updated);
        Optional<StockMovement> result = stockMovementService.update(1L, updated);
        assertTrue(result.isPresent());
        assertEquals(10, result.get().getQuantity());
    }

    @Test
    void testDelete() {
        when(stockMovementRepository.existsById(1L)).thenReturn(true);
        doNothing().when(stockMovementRepository).deleteById(1L);
        boolean deleted = stockMovementService.delete(1L);
        assertTrue(deleted);
    }
}
