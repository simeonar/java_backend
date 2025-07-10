package com.sagedemo.backend.production.controller;

import com.sagedemo.backend.production.entity.ProductionOrder;
import com.sagedemo.backend.production.service.ProductionOrderService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/production/orders")
public class ProductionOrderController {
    private final ProductionOrderService productionOrderService;
    public ProductionOrderController(ProductionOrderService productionOrderService) {
        this.productionOrderService = productionOrderService;
    }
    @PostMapping
    public ProductionOrder createOrder(@RequestParam Long bomId, @RequestParam Integer quantity) {
        return productionOrderService.createProductionOrder(bomId, quantity);
    }
    @GetMapping
    public List<ProductionOrder> getAllOrders() {
        return productionOrderService.getAllProductionOrders();
    }
}
