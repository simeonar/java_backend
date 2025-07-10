package com.sagedemo.backend.sales.controller;

import com.sagedemo.backend.sales.dto.InvoiceDTO;
import com.sagedemo.backend.sales.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<InvoiceDTO> createInvoice(@RequestParam Long salesOrderId) {
        InvoiceDTO createdInvoice = invoiceService.createInvoiceForOrder(salesOrderId);
        return ResponseEntity.ok(createdInvoice);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        List<InvoiceDTO> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }
}
