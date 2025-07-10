package com.sagedemo.backend.purchasing.service;

import com.sagedemo.backend.purchasing.dto.SupplierDTO;
import com.sagedemo.backend.purchasing.entity.Supplier;
import com.sagedemo.backend.purchasing.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplierServiceTest {
    @Mock
    private SupplierRepository supplierRepository;
    @InjectMocks
    private SupplierService supplierService;
    public SupplierServiceTest() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetAllSuppliersReturnsEmptyList() {
        when(supplierRepository.findAll()).thenReturn(Collections.emptyList());
        List<SupplierDTO> result = supplierService.getAllSuppliers();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
