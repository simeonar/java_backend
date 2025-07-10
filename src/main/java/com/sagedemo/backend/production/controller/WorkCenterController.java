package com.sagedemo.backend.production.controller;

import com.sagedemo.backend.production.entity.WorkCenter;
import com.sagedemo.backend.production.service.WorkCenterService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/production/work-centers")
public class WorkCenterController {
    private final WorkCenterService workCenterService;
    public WorkCenterController(WorkCenterService workCenterService) {
        this.workCenterService = workCenterService;
    }
    @GetMapping
    public List<WorkCenter> getAllWorkCenters() {
        return workCenterService.getAllWorkCenters();
    }
    @PostMapping
    public WorkCenter createWorkCenter(@RequestBody WorkCenter wc) {
        return workCenterService.createWorkCenter(wc);
    }
    @PutMapping("/{id}")
    public WorkCenter updateWorkCenter(@PathVariable Long id, @RequestBody WorkCenter wc) {
        return workCenterService.updateWorkCenter(id, wc);
    }
    @DeleteMapping("/{id}")
    public void deleteWorkCenter(@PathVariable Long id) {
        workCenterService.deleteWorkCenter(id);
    }
}
