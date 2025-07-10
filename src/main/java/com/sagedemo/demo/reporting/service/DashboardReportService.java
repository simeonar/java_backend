package com.sagedemo.demo.reporting.service;

import com.sagedemo.demo.reporting.dto.DashboardReportDTO;
import com.sagedemo.demo.sales.repository.InvoiceRepository;
import com.sagedemo.demo.purchasing.repository.PurchaseOrderRepository;
import com.sagedemo.demo.inventory.repository.ProductRepository;
import com.sagedemo.demo.sales.repository.CustomerRepository;
import com.sagedemo.demo.purchasing.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class DashboardReportService {
    private final InvoiceRepository invoiceRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    public DashboardReportService(InvoiceRepository invoiceRepository, PurchaseOrderRepository purchaseOrderRepository, ProductRepository productRepository, CustomerRepository customerRepository, SupplierRepository supplierRepository) {
        this.invoiceRepository = invoiceRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.supplierRepository = supplierRepository;
    }
    public DashboardReportDTO getDashboardReport() {
        DashboardReportDTO dto = new DashboardReportDTO();
        dto.setTotalSales(invoiceRepository.findAll().stream().map(i -> i.getTotalAmount() != null ? i.getTotalAmount() : BigDecimal.ZERO).reduce(BigDecimal.ZERO, BigDecimal::add));
        dto.setTotalPurchases(purchaseOrderRepository.findAll().stream().map(po -> po.getTotalAmount() != null ? po.getTotalAmount() : BigDecimal.ZERO).reduce(BigDecimal.ZERO, BigDecimal::add));
        dto.setTotalProducts((int) productRepository.count());
        dto.setTotalCustomers((int) customerRepository.count());
        dto.setTotalSuppliers((int) supplierRepository.count());
        return dto;
    }
}
