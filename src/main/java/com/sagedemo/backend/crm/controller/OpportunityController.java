package com.sagedemo.backend.crm.controller;

import com.sagedemo.backend.crm.entity.Opportunity;
import com.sagedemo.backend.crm.service.OpportunityService;
import com.sagedemo.backend.common.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/crm/opportunities")
public class OpportunityController {
    private final OpportunityService opportunityService;

    public OpportunityController(OpportunityService opportunityService) {
        this.opportunityService = opportunityService;
    }

    @GetMapping
    public ApiResponse<List<Opportunity>> getAll() {
        return ApiResponse.ok(opportunityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Opportunity>> getById(@PathVariable Long id) {
        return opportunityService.findById(id)
                .map(o -> ResponseEntity.ok(ApiResponse.ok(o)))
                .orElse(ResponseEntity.ok(ApiResponse.error("Opportunity not found", "NOT_FOUND")));
    }

    @PostMapping
    public ApiResponse<Opportunity> create(@Valid @RequestBody Opportunity opportunity) {
        return ApiResponse.ok(opportunityService.save(opportunity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Opportunity>> update(@PathVariable Long id, @Valid @RequestBody Opportunity opportunity) {
        return opportunityService.update(id, opportunity)
                .map(updated -> ResponseEntity.ok(ApiResponse.ok(updated)))
                .orElse(ResponseEntity.ok(ApiResponse.error("Opportunity not found", "NOT_FOUND")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        if (opportunityService.delete(id)) {
            return ResponseEntity.ok(ApiResponse.ok(null));
        } else {
            return ResponseEntity.ok(ApiResponse.error("Opportunity not found", "NOT_FOUND"));
        }
    }
}
