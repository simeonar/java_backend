package com.sagedemo.backend.production.controller;

import com.sagedemo.backend.production.entity.BillOfMaterials;
import com.sagedemo.backend.production.service.BillOfMaterialsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bom")
public class BillOfMaterialsController {
    private final BillOfMaterialsService billOfMaterialsService;

    public BillOfMaterialsController(BillOfMaterialsService billOfMaterialsService) {
        this.billOfMaterialsService = billOfMaterialsService;
    }

    @GetMapping
    public List<BillOfMaterials> getAll() {
        return billOfMaterialsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillOfMaterials> getById(@PathVariable Long id) {
        return billOfMaterialsService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BillOfMaterials create(@RequestBody BillOfMaterials bom) {
        return billOfMaterialsService.save(bom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillOfMaterials> update(@PathVariable Long id, @RequestBody BillOfMaterials bom) {
        return billOfMaterialsService.update(id, bom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (billOfMaterialsService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
