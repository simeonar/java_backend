package com.sagedemo.demo.reporting.service;

import com.sagedemo.demo.inventory.entity.Inventory;
import com.sagedemo.demo.inventory.repository.InventoryRepository;
import com.sagedemo.demo.reporting.dto.StockLevelReportDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockLevelReportService {
    private final InventoryRepository inventoryRepository;
    public StockLevelReportService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    public StockLevelReportDTO getStockLevelReport() {
        List<Inventory> inventoryList = inventoryRepository.findAll();
        List<StockLevelReportDTO.StockLevelItem> items = inventoryList.stream().map(inv -> {
            StockLevelReportDTO.StockLevelItem item = new StockLevelReportDTO.StockLevelItem();
            item.setProductId(inv.getProduct().getId());
            item.setProductName(inv.getProduct().getName());
            item.setWarehouseName(inv.getWarehouse().getName());
            item.setQuantity(inv.getQuantity());
            return item;
        }).collect(Collectors.toList());
        StockLevelReportDTO dto = new StockLevelReportDTO();
        dto.setItems(items);
        return dto;
    }
}
