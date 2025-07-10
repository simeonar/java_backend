package com.sagedemo.backend.purchasing.controller;

import com.sagedemo.backend.purchasing.dto.PurchaseOrderDTO;
import com.sagedemo.backend.purchasing.service.PurchaseOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/purchasing/orders")
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }
    @PostMapping
    public ResponseEntity<PurchaseOrderDTO> createOrder(@RequestBody PurchaseOrderDTO dto) {
        return ResponseEntity.ok(purchaseOrderService.createPurchaseOrder(dto));
    }
    @GetMapping
    public ResponseEntity<List<PurchaseOrderDTO>> getAllOrders() {
        return ResponseEntity.ok(purchaseOrderService.getAllPurchaseOrders());
    }
}
