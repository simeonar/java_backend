package com.sagedemo.demo.inventory.service;

import com.sagedemo.demo.inventory.dto.InventoryDTO;
import com.sagedemo.demo.inventory.entity.Inventory;
import com.sagedemo.demo.inventory.entity.Product;
import com.sagedemo.demo.inventory.entity.StockMovement;
import com.sagedemo.demo.inventory.entity.Warehouse;
import com.sagedemo.demo.inventory.repository.InventoryRepository;
import com.sagedemo.demo.inventory.repository.ProductRepository;
import com.sagedemo.demo.inventory.repository.StockMovementRepository;
import com.sagedemo.demo.inventory.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockMovementRepository stockMovementRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository, WarehouseRepository warehouseRepository, StockMovementRepository stockMovementRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryDTO> getStockByProduct(Long productId) {
        return inventoryRepository.findByProductId(productId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void moveStock(Long productId, Long fromWarehouseId, Long toWarehouseId, int quantity, String reference) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Warehouse toWarehouse = warehouseRepository.findById(toWarehouseId).orElseThrow(() -> new RuntimeException("To-Warehouse not found"));

        StockMovement.MovementType type;
        if (fromWarehouseId != null) {
            // Transfer or Outbound
            Warehouse fromWarehouse = warehouseRepository.findById(fromWarehouseId).orElseThrow(() -> new RuntimeException("From-Warehouse not found"));
            updateInventory(product, fromWarehouse, -quantity);
            type = StockMovement.MovementType.TRANSFER;
        } else {
            // Inbound
            type = StockMovement.MovementType.INBOUND;
        }

        updateInventory(product, toWarehouse, quantity);

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setFromWarehouse(fromWarehouseId != null ? warehouseRepository.findById(fromWarehouseId).get() : null);
        movement.setToWarehouse(toWarehouse);
        movement.setQuantity(quantity);
        movement.setType(type);
        movement.setMovementDate(LocalDateTime.now());
        movement.setReference(reference);
        stockMovementRepository.save(movement);
    }

    private void updateInventory(Product product, Warehouse warehouse, int quantityChange) {
        Inventory inventory = inventoryRepository.findByProductIdAndWarehouseId(product.getId(), warehouse.getId())
                .orElseGet(() -> {
                    Inventory newInventory = new Inventory();
                    newInventory.setProduct(product);
                    newInventory.setWarehouse(warehouse);
                    newInventory.setQuantity(0);
                    newInventory.setReservedQuantity(0);
                    return newInventory;
                });

        inventory.setQuantity(inventory.getQuantity() + quantityChange);
        inventory.setLastUpdated(LocalDateTime.now());
        inventoryRepository.save(inventory);
    }

    private InventoryDTO toDto(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(inventory.getId());
        dto.setProductId(inventory.getProduct().getId());
        dto.setProductName(inventory.getProduct().getName());
        dto.setWarehouseId(inventory.getWarehouse().getId());
        dto.setWarehouseName(inventory.getWarehouse().getName());
        dto.setQuantity(inventory.getQuantity());
        dto.setReservedQuantity(inventory.getReservedQuantity());
        dto.setLastUpdated(inventory.getLastUpdated());
        return dto;
    }
}
