package com.sagedemo.demo.sales.controller;

import com.sagedemo.demo.sales.dto.SalesOrderDTO;
import com.sagedemo.demo.sales.service.SalesOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales/orders")
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    public SalesOrderController(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @PostMapping
    public ResponseEntity<SalesOrderDTO> createSalesOrder(@RequestBody SalesOrderDTO salesOrderDTO) {
        SalesOrderDTO createdOrder = salesOrderService.createSalesOrder(salesOrderDTO);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping
    public ResponseEntity<List<SalesOrderDTO>> getAllSalesOrders() {
        List<SalesOrderDTO> orders = salesOrderService.getAllSalesOrders();
        return ResponseEntity.ok(orders);
    }
}
