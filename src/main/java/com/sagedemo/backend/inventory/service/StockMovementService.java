package com.sagedemo.backend.inventory.service;

import com.sagedemo.backend.inventory.entity.StockMovement;
import com.sagedemo.backend.inventory.repository.StockMovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;

@Service
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    public StockMovementService(StockMovementRepository stockMovementRepository) {
        this.stockMovementRepository = stockMovementRepository;
    }
    @Transactional
    public StockMovement createStockMovement(StockMovement movement) {
        movement.setMovementDate(LocalDateTime.now());
        return stockMovementRepository.save(movement);
    }
    public List<StockMovement> getAllMovements() {
        return stockMovementRepository.findAll();
    }

    public Optional<StockMovement> findById(Long id) {
        return stockMovementRepository.findById(id);
    }

    @Transactional
    public StockMovement save(StockMovement movement) {
        if (movement.getMovementDate() == null) {
            movement.setMovementDate(LocalDateTime.now());
        }
        return stockMovementRepository.save(movement);
    }

    @Transactional
    public Optional<StockMovement> update(Long id, StockMovement updated) {
        return stockMovementRepository.findById(id).map(existing -> {
            existing.setProduct(updated.getProduct());
            existing.setFromWarehouse(updated.getFromWarehouse());
            existing.setToWarehouse(updated.getToWarehouse());
            existing.setQuantity(updated.getQuantity());
            existing.setType(updated.getType());
            existing.setMovementDate(updated.getMovementDate());
            existing.setReference(updated.getReference());
            return stockMovementRepository.save(existing);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        if (stockMovementRepository.existsById(id)) {
            stockMovementRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
