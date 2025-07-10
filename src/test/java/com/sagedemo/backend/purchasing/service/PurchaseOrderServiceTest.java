package com.sagedemo.backend.purchasing.service;

import com.sagedemo.backend.purchasing.dto.PurchaseOrderDTO;
import com.sagedemo.backend.purchasing.entity.PurchaseOrder;
import com.sagedemo.backend.purchasing.repository.PurchaseOrderRepository;
import com.sagedemo.backend.purchasing.repository.SupplierRepository;
import com.sagedemo.backend.inventory.repository.ProductRepository;
import com.sagedemo.backend.inventory.repository.WarehouseRepository;
import com.sagedemo.backend.inventory.service.InventoryService;
import com.sagedemo.backend.finance.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PurchaseOrderServiceTest {
    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;
    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private WarehouseRepository warehouseRepository;
    @Mock
    private InventoryService inventoryService;
    @Mock
    private TransactionService transactionService;
    @InjectMocks
    private PurchaseOrderService purchaseOrderService;

    public PurchaseOrderServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPurchaseOrdersReturnsEmptyList() {
        when(purchaseOrderRepository.findAll()).thenReturn(Collections.emptyList());
        List<PurchaseOrderDTO> result = purchaseOrderService.getAllPurchaseOrders();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
