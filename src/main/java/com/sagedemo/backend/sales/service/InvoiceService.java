package com.sagedemo.backend.sales.service;

import com.sagedemo.backend.sales.dto.InvoiceDTO;
import com.sagedemo.backend.sales.entity.Invoice;
import com.sagedemo.backend.sales.entity.InvoiceStatus;
import com.sagedemo.backend.sales.entity.SalesOrder;
import com.sagedemo.backend.sales.repository.InvoiceRepository;
import com.sagedemo.backend.sales.repository.SalesOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sagedemo.backend.finance.service.TransactionService;
import com.sagedemo.backend.finance.dto.TransactionDTO;
import com.sagedemo.backend.finance.util.FinanceAccountConstants;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final TransactionService transactionService;

    public InvoiceService(InvoiceRepository invoiceRepository, SalesOrderRepository salesOrderRepository, TransactionService transactionService) {
        this.invoiceRepository = invoiceRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.transactionService = transactionService;
    }

    @Transactional
    public InvoiceDTO createInvoiceForOrder(Long salesOrderId) {
        SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId)
                .orElseThrow(() -> new RuntimeException("SalesOrder not found"));

        Invoice invoice = new Invoice();
        invoice.setSalesOrder(salesOrder);
        invoice.setInvoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now().plusDays(30));
        invoice.setTotalAmount(salesOrder.getTotalAmount());
        invoice.setStatus(InvoiceStatus.DRAFT);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        // Finance integration: create transaction (Bank <- Sales Revenue)
        TransactionDTO tx = new TransactionDTO();
        tx.setTransactionDate(savedInvoice.getInvoiceDate().atStartOfDay());
        tx.setDescription("Invoice " + savedInvoice.getInvoiceNumber());
        tx.setAmount(savedInvoice.getTotalAmount());
        tx.setDebitAccountId(FinanceAccountConstants.BANK_ACCOUNT_ID);
        tx.setCreditAccountId(FinanceAccountConstants.SALES_REVENUE_ACCOUNT_ID);
        transactionService.createTransaction(tx);

        return toDto(savedInvoice);
    }

    @Transactional(readOnly = true)
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private InvoiceDTO toDto(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(invoice.getId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setSalesOrderId(invoice.getSalesOrder().getId());
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setDueDate(invoice.getDueDate());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setStatus(invoice.getStatus());
        return dto;
    }
}
