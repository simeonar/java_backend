package com.sagedemo.backend.production.service;

import com.sagedemo.backend.production.entity.ProductionSchedule;
import com.sagedemo.backend.production.repository.ProductionScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductionScheduleService {
    private final ProductionScheduleRepository productionScheduleRepository;
    public ProductionScheduleService(ProductionScheduleRepository productionScheduleRepository) {
        this.productionScheduleRepository = productionScheduleRepository;
    }
    @Transactional
    public ProductionSchedule createSchedule(ProductionSchedule schedule) {
        return productionScheduleRepository.save(schedule);
    }
    public List<ProductionSchedule> getAllSchedules() {
        return productionScheduleRepository.findAll();
    }
}
