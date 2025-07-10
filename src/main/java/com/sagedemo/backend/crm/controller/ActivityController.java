package com.sagedemo.backend.crm.controller;

import com.sagedemo.backend.crm.entity.Activity;
import com.sagedemo.backend.crm.service.ActivityService;
import com.sagedemo.backend.common.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/crm/activities")
public class ActivityController {
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public ApiResponse<List<Activity>> getAll() {
        return ApiResponse.ok(activityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Activity>> getById(@PathVariable Long id) {
        return activityService.findById(id)
                .map(a -> ResponseEntity.ok(ApiResponse.ok(a)))
                .orElse(ResponseEntity.ok(ApiResponse.error("Activity not found", "NOT_FOUND")));
    }

    @PostMapping
    public ApiResponse<Activity> create(@Valid @RequestBody Activity activity) {
        return ApiResponse.ok(activityService.save(activity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Activity>> update(@PathVariable Long id, @Valid @RequestBody Activity activity) {
        return activityService.update(id, activity)
                .map(updated -> ResponseEntity.ok(ApiResponse.ok(updated)))
                .orElse(ResponseEntity.ok(ApiResponse.error("Activity not found", "NOT_FOUND")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        if (activityService.delete(id)) {
            return ResponseEntity.ok(ApiResponse.ok(null));
        } else {
            return ResponseEntity.ok(ApiResponse.error("Activity not found", "NOT_FOUND"));
        }
    }
}
