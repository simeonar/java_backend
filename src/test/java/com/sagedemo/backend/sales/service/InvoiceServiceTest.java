package com.sagedemo.backend.sales.service;

import com.sagedemo.backend.finance.dto.TransactionDTO;
import com.sagedemo.backend.finance.service.TransactionService;
import com.sagedemo.backend.sales.dto.InvoiceDTO;
import com.sagedemo.backend.sales.entity.Invoice;
import com.sagedemo.backend.sales.entity.InvoiceStatus;
import com.sagedemo.backend.sales.entity.SalesOrder;
import com.sagedemo.backend.sales.repository.InvoiceRepository;
import com.sagedemo.backend.sales.repository.SalesOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InvoiceServiceTest {
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private SalesOrderRepository salesOrderRepository;
    @Mock
    private TransactionService transactionService;
    @InjectMocks
    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createInvoiceForOrder_shouldSaveAndReturnInvoiceDTO_andCreateTransaction() {
        SalesOrder order = new SalesOrder();
        order.setId(1L);
        order.setTotalAmount(BigDecimal.valueOf(500));
        Invoice saved = new Invoice();
        saved.setId(10L);
        saved.setSalesOrder(order);
        saved.setInvoiceNumber("INV-12345678");
        saved.setInvoiceDate(LocalDate.now());
        saved.setDueDate(LocalDate.now().plusDays(30));
        saved.setTotalAmount(BigDecimal.valueOf(500));
        saved.setStatus(InvoiceStatus.DRAFT);

        when(salesOrderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(saved);

        InvoiceDTO result = invoiceService.createInvoiceForOrder(1L);
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("INV-12345678", result.getInvoiceNumber());
        assertEquals(BigDecimal.valueOf(500), result.getTotalAmount());
        verify(transactionService, times(1)).createTransaction(any(TransactionDTO.class));
    }

    @Test
    void createInvoiceForOrder_shouldThrowIfOrderNotFound() {
        when(salesOrderRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> invoiceService.createInvoiceForOrder(99L));
        assertTrue(ex.getMessage().contains("SalesOrder not found"));
    }

    @Test
    void getAllInvoices_shouldReturnListOfInvoiceDTO() {
        SalesOrder order = new SalesOrder();
        order.setId(1L);
        Invoice invoice = new Invoice();
        invoice.setId(2L);
        invoice.setSalesOrder(order);
        invoice.setInvoiceNumber("INV-1");
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now().plusDays(30));
        invoice.setTotalAmount(BigDecimal.valueOf(100));
        invoice.setStatus(InvoiceStatus.DRAFT);
        when(invoiceRepository.findAll()).thenReturn(List.of(invoice));
        List<InvoiceDTO> result = invoiceService.getAllInvoices();
        assertEquals(1, result.size());
        assertEquals("INV-1", result.get(0).getInvoiceNumber());
        assertEquals(BigDecimal.valueOf(100), result.get(0).getTotalAmount());
    }
}
