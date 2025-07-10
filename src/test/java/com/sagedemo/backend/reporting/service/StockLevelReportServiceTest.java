package com.sagedemo.backend.reporting.service;

import com.sagedemo.backend.inventory.entity.Inventory;
import com.sagedemo.backend.inventory.entity.Product;
import com.sagedemo.backend.inventory.entity.Warehouse;
import com.sagedemo.backend.inventory.repository.InventoryRepository;
import com.sagedemo.backend.reporting.dto.StockLevelReportDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockLevelReportServiceTest {
    @Mock
    private InventoryRepository inventoryRepository;
    @InjectMocks
    private StockLevelReportService stockLevelReportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStockLevelReport_returnsItems() {
        Inventory inv = new Inventory();
        Product product = new Product();
        product.setId(1L);
        product.setName("TestProduct");
        Warehouse warehouse = new Warehouse();
        warehouse.setName("MainWH");
        inv.setProduct(product);
        inv.setWarehouse(warehouse);
        inv.setQuantity(42);
        when(inventoryRepository.findAll()).thenReturn(List.of(inv));
        StockLevelReportDTO dto = stockLevelReportService.getStockLevelReport();
        assertEquals(1, dto.getItems().size());
        assertEquals("TestProduct", dto.getItems().get(0).getProductName());
        assertEquals("MainWH", dto.getItems().get(0).getWarehouseName());
        assertEquals(42, dto.getItems().get(0).getQuantity());
    }
}
