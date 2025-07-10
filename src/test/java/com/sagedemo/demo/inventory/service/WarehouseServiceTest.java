package com.sagedemo.demo.inventory.service;

import com.sagedemo.demo.inventory.dto.WarehouseDTO;
import com.sagedemo.demo.inventory.entity.Warehouse;
import com.sagedemo.demo.inventory.repository.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WarehouseServiceTest {
    @Mock
    private WarehouseRepository warehouseRepository;
    @InjectMocks
    private WarehouseService warehouseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createWarehouse_shouldSaveAndReturnWarehouseDTO() {
        WarehouseDTO dto = new WarehouseDTO();
        dto.setName("Main Warehouse");
        dto.setLocation("Moscow");

        Warehouse saved = new Warehouse();
        saved.setId(1L);
        saved.setName("Main Warehouse");
        saved.setLocation("Moscow");

        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(saved);

        WarehouseDTO result = warehouseService.createWarehouse(dto);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Main Warehouse", result.getName());
        assertEquals("Moscow", result.getLocation());
    }

    @Test
    void getAllWarehouses_shouldReturnListOfWarehouseDTO() {
        Warehouse w1 = new Warehouse();
        w1.setId(1L);
        w1.setName("W1");
        w1.setLocation("Moscow");
        Warehouse w2 = new Warehouse();
        w2.setId(2L);
        w2.setName("W2");
        w2.setLocation("SPB");
        when(warehouseRepository.findAll()).thenReturn(Arrays.asList(w1, w2));
        List<WarehouseDTO> result = warehouseService.getAllWarehouses();
        assertEquals(2, result.size());
        assertEquals("W1", result.get(0).getName());
        assertEquals("SPB", result.get(1).getLocation());
    }
}
