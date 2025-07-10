package com.sagedemo.backend.crm.controller;

import com.sagedemo.backend.crm.entity.Lead;
import com.sagedemo.backend.crm.service.LeadService;
import com.sagedemo.backend.common.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/crm/leads")
public class LeadController {
    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @GetMapping
    public ApiResponse<List<Lead>> getAll() {
        return ApiResponse.ok(leadService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Lead>> getById(@PathVariable Long id) {
        return leadService.findById(id)
                .map(lead -> ResponseEntity.ok(ApiResponse.ok(lead)))
                .orElse(ResponseEntity.ok(ApiResponse.error("Lead not found", "NOT_FOUND")));
    }

    @PostMapping
    public ApiResponse<Lead> create(@Valid @RequestBody Lead lead) {
        return ApiResponse.ok(leadService.save(lead));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Lead>> update(@PathVariable Long id, @Valid @RequestBody Lead lead) {
        return leadService.update(id, lead)
                .map(updated -> ResponseEntity.ok(ApiResponse.ok(updated)))
                .orElse(ResponseEntity.ok(ApiResponse.error("Lead not found", "NOT_FOUND")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        if (leadService.delete(id)) {
            return ResponseEntity.ok(ApiResponse.ok(null));
        } else {
            return ResponseEntity.ok(ApiResponse.error("Lead not found", "NOT_FOUND"));
        }
    }
}
