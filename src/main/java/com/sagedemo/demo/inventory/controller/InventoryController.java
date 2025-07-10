package com.sagedemo.demo.inventory.controller;

import com.sagedemo.demo.inventory.dto.InventoryDTO;
import com.sagedemo.demo.inventory.dto.StockMovementDTO;
import com.sagedemo.demo.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/stock/{productId}")
    public ResponseEntity<List<InventoryDTO>> getStockByProduct(@PathVariable Long productId) {
        List<InventoryDTO> stock = inventoryService.getStockByProduct(productId);
        return ResponseEntity.ok(stock);
    }

    @PostMapping("/movements")
    public ResponseEntity<Void> moveStock(@RequestBody StockMovementDTO movementDTO) {
        inventoryService.moveStock(
                movementDTO.getProductId(),
                movementDTO.getFromWarehouseId(),
                movementDTO.getToWarehouseId(),
                movementDTO.getQuantity(),
                movementDTO.getReference()
        );
        return ResponseEntity.ok().build();
    }
}
