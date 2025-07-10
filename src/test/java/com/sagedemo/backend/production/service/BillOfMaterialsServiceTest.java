package com.sagedemo.backend.production.service;

import com.sagedemo.backend.production.entity.BillOfMaterials;
import com.sagedemo.backend.production.repository.BillOfMaterialsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BillOfMaterialsServiceTest {
    @Mock
    private BillOfMaterialsRepository billOfMaterialsRepository;
    @InjectMocks
    private BillOfMaterialsService billOfMaterialsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_returnsList() {
        BillOfMaterials bom = new BillOfMaterials();
        when(billOfMaterialsRepository.findAll()).thenReturn(List.of(bom));
        List<BillOfMaterials> result = billOfMaterialsService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void findById_found() {
        BillOfMaterials bom = new BillOfMaterials();
        when(billOfMaterialsRepository.findById(1L)).thenReturn(Optional.of(bom));
        Optional<BillOfMaterials> result = billOfMaterialsService.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void findById_notFound() {
        when(billOfMaterialsRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<BillOfMaterials> result = billOfMaterialsService.findById(2L);
        assertTrue(result.isEmpty());
    }

    @Test
    void save_success() {
        BillOfMaterials bom = new BillOfMaterials();
        when(billOfMaterialsRepository.save(bom)).thenReturn(bom);
        BillOfMaterials result = billOfMaterialsService.save(bom);
        assertNotNull(result);
    }

    @Test
    void update_found() {
        BillOfMaterials existing = new BillOfMaterials();
        existing.setName("Old");
        BillOfMaterials updated = new BillOfMaterials();
        updated.setName("New");
        when(billOfMaterialsRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(billOfMaterialsRepository.save(any(BillOfMaterials.class))).thenReturn(updated);
        Optional<BillOfMaterials> result = billOfMaterialsService.update(1L, updated);
        assertTrue(result.isPresent());
        assertEquals("New", result.get().getName());
    }

    @Test
    void update_notFound() {
        BillOfMaterials updated = new BillOfMaterials();
        when(billOfMaterialsRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<BillOfMaterials> result = billOfMaterialsService.update(2L, updated);
        assertTrue(result.isEmpty());
    }

    @Test
    void delete_found() {
        when(billOfMaterialsRepository.existsById(1L)).thenReturn(true);
        doNothing().when(billOfMaterialsRepository).deleteById(1L);
        boolean result = billOfMaterialsService.delete(1L);
        assertTrue(result);
    }

    @Test
    void delete_notFound() {
        when(billOfMaterialsRepository.existsById(2L)).thenReturn(false);
        boolean result = billOfMaterialsService.delete(2L);
        assertFalse(result);
    }
}
