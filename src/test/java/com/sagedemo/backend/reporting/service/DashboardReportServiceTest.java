package com.sagedemo.backend.reporting.service;

import com.sagedemo.backend.reporting.dto.DashboardReportDTO;
import com.sagedemo.backend.sales.entity.Invoice;
import com.sagedemo.backend.sales.repository.InvoiceRepository;
import com.sagedemo.backend.purchasing.entity.PurchaseOrder;
import com.sagedemo.backend.purchasing.repository.PurchaseOrderRepository;
import com.sagedemo.backend.inventory.repository.ProductRepository;
import com.sagedemo.backend.sales.repository.CustomerRepository;
import com.sagedemo.backend.purchasing.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DashboardReportServiceTest {
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private SupplierRepository supplierRepository;
    @InjectMocks
    private DashboardReportService dashboardReportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDashboardReport_returnsCorrectTotals() {
        Invoice invoice = new Invoice();
        invoice.setTotalAmount(BigDecimal.valueOf(100));
        when(invoiceRepository.findAll()).thenReturn(List.of(invoice));
        PurchaseOrder po = new PurchaseOrder();
        po.setTotalAmount(BigDecimal.valueOf(50));
        when(purchaseOrderRepository.findAll()).thenReturn(List.of(po));
        when(productRepository.count()).thenReturn(5L);
        when(customerRepository.count()).thenReturn(3L);
        when(supplierRepository.count()).thenReturn(2L);
        DashboardReportDTO dto = dashboardReportService.getDashboardReport();
        assertEquals(BigDecimal.valueOf(100), dto.getTotalSales());
        assertEquals(BigDecimal.valueOf(50), dto.getTotalPurchases());
        assertEquals(5, dto.getTotalProducts());
        assertEquals(3, dto.getTotalCustomers());
        assertEquals(2, dto.getTotalSuppliers());
    }
}
