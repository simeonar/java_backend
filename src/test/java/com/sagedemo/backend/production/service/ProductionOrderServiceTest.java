package com.sagedemo.backend.production.service;

import com.sagedemo.backend.production.entity.BillOfMaterials;
import com.sagedemo.backend.production.entity.ProductionOrder;
import com.sagedemo.backend.production.entity.ProductionOrderStatus;
import com.sagedemo.backend.production.repository.BillOfMaterialsRepository;
import com.sagedemo.backend.production.repository.ProductionOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductionOrderServiceTest {
    @Mock
    private ProductionOrderRepository productionOrderRepository;
    @Mock
    private BillOfMaterialsRepository billOfMaterialsRepository;
    @InjectMocks
    private ProductionOrderService productionOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProductionOrder_success() {
        BillOfMaterials bom = new BillOfMaterials();
        bom.setId(1L);
        bom.setName("Test BOM");
        when(billOfMaterialsRepository.findById(1L)).thenReturn(Optional.of(bom));
        ProductionOrder savedOrder = new ProductionOrder();
        savedOrder.setId(10L);
        when(productionOrderRepository.save(any(ProductionOrder.class))).thenReturn(savedOrder);

        ProductionOrder result = productionOrderService.createProductionOrder(1L, 5);
        assertNotNull(result);
        verify(productionOrderRepository).save(any(ProductionOrder.class));
        assertEquals(10L, result.getId());
    }

    @Test
    void createProductionOrder_bomNotFound() {
        when(billOfMaterialsRepository.findById(2L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            productionOrderService.createProductionOrder(2L, 3)
        );
        assertTrue(ex.getMessage().contains("BOM not found"));
    }

    @Test
    void getAllProductionOrders_returnsList() {
        ProductionOrder order = new ProductionOrder();
        when(productionOrderRepository.findAll()).thenReturn(List.of(order));
        List<ProductionOrder> result = productionOrderService.getAllProductionOrders();
        assertEquals(1, result.size());
    }
}
