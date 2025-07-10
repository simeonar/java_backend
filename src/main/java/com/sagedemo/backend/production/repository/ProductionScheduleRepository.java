package com.sagedemo.backend.production.repository;

import com.sagedemo.backend.production.entity.ProductionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionScheduleRepository extends JpaRepository<ProductionSchedule, Long> {
}
