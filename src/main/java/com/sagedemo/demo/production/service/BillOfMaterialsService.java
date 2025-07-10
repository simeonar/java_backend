package com.sagedemo.demo.production.service;

import com.sagedemo.demo.production.entity.BillOfMaterials;
import com.sagedemo.demo.production.repository.BillOfMaterialsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BillOfMaterialsService {
    private final BillOfMaterialsRepository billOfMaterialsRepository;

    public BillOfMaterialsService(BillOfMaterialsRepository billOfMaterialsRepository) {
        this.billOfMaterialsRepository = billOfMaterialsRepository;
    }

    public List<BillOfMaterials> findAll() {
        return billOfMaterialsRepository.findAll();
    }

    public Optional<BillOfMaterials> findById(Long id) {
        return billOfMaterialsRepository.findById(id);
    }

    @Transactional
    public BillOfMaterials save(BillOfMaterials bom) {
        return billOfMaterialsRepository.save(bom);
    }

    @Transactional
    public Optional<BillOfMaterials> update(Long id, BillOfMaterials updated) {
        return billOfMaterialsRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setComponents(updated.getComponents());
            return billOfMaterialsRepository.save(existing);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        if (billOfMaterialsRepository.existsById(id)) {
            billOfMaterialsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
