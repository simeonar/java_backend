package com.sagedemo.demo.crm.controller;

import com.sagedemo.demo.crm.entity.Lead;
import com.sagedemo.demo.crm.service.LeadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crm/leads")
public class LeadController {
    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @GetMapping
    public List<Lead> getAll() {
        return leadService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lead> getById(@PathVariable Long id) {
        return leadService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Lead create(@RequestBody Lead lead) {
        return leadService.save(lead);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lead> update(@PathVariable Long id, @RequestBody Lead lead) {
        return leadService.update(id, lead)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (leadService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
