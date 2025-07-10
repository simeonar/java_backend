package com.sagedemo.demo.production.controller;

import com.sagedemo.demo.production.entity.ProductionSchedule;
import com.sagedemo.demo.production.service.ProductionScheduleService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/production/schedule")
public class ProductionScheduleController {
    private final ProductionScheduleService productionScheduleService;
    public ProductionScheduleController(ProductionScheduleService productionScheduleService) {
        this.productionScheduleService = productionScheduleService;
    }
    @PostMapping
    public ProductionSchedule createSchedule(@RequestBody ProductionSchedule schedule) {
        return productionScheduleService.createSchedule(schedule);
    }
    @GetMapping
    public List<ProductionSchedule> getAllSchedules() {
        return productionScheduleService.getAllSchedules();
    }
}
