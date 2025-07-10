package com.sagedemo.demo.purchasing.service;

import com.sagedemo.demo.inventory.entity.Product;
import com.sagedemo.demo.inventory.repository.ProductRepository;
import com.sagedemo.demo.inventory.repository.WarehouseRepository;
import com.sagedemo.demo.inventory.service.InventoryService;
import com.sagedemo.demo.purchasing.dto.PurchaseOrderDTO;
import com.sagedemo.demo.purchasing.dto.PurchaseOrderItemDTO;
import com.sagedemo.demo.purchasing.entity.PurchaseOrder;
import com.sagedemo.demo.purchasing.entity.PurchaseOrderItem;
import com.sagedemo.demo.purchasing.entity.Supplier;
import com.sagedemo.demo.purchasing.repository.PurchaseOrderRepository;
import com.sagedemo.demo.purchasing.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryService inventoryService;
    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, SupplierRepository supplierRepository, ProductRepository productRepository, WarehouseRepository warehouseRepository, InventoryService inventoryService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.inventoryService = inventoryService;
    }
    @Transactional
    public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderDTO dto) {
        Supplier supplier = supplierRepository.findById(dto.getSupplierId()).orElseThrow(() -> new RuntimeException("Supplier not found"));
        PurchaseOrder order = new PurchaseOrder();
        order.setSupplier(supplier);
        order.setOrderDate(LocalDate.now());
        order.setOrderNumber("PO-" + System.currentTimeMillis());
        order.setItems(new ArrayList<>());
        BigDecimal total = BigDecimal.ZERO;
        // For demo: always use the first warehouse
        var warehouse = warehouseRepository.findAll().stream().findFirst().orElseThrow(() -> new RuntimeException("No warehouse found"));
        for (PurchaseOrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
            PurchaseOrderItem item = new PurchaseOrderItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getUnitPrice());
            item.setTotalPrice(itemDTO.getUnitPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
            item.setPurchaseOrder(order);
            order.getItems().add(item);
            total = total.add(item.getTotalPrice());
            // Inventory integration: add stock
            inventoryService.moveStock(product.getId(), null, warehouse.getId(), item.getQuantity(), "PO:" + order.getOrderNumber());
        }
        order.setTotalAmount(total);
        PurchaseOrder saved = purchaseOrderRepository.save(order);
        return toDto(saved);
    }
    @Transactional(readOnly = true)
    public List<PurchaseOrderDTO> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }
    private PurchaseOrderDTO toDto(PurchaseOrder order) {
        PurchaseOrderDTO dto = new PurchaseOrderDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setSupplierId(order.getSupplier().getId());
        dto.setSupplierName(order.getSupplier().getName());
        dto.setOrderDate(order.getOrderDate() != null ? order.getOrderDate().toString() : null);
        dto.setTotalAmount(order.getTotalAmount());
        dto.setItems(order.getItems().stream().map(this::toDto).collect(Collectors.toList()));
        return dto;
    }
    private PurchaseOrderItemDTO toDto(PurchaseOrderItem item) {
        PurchaseOrderItemDTO dto = new PurchaseOrderItemDTO();
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        return dto;
    }
}
