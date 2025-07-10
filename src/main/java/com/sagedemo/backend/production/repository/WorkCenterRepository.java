package com.sagedemo.backend.production.repository;

import com.sagedemo.backend.production.entity.WorkCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkCenterRepository extends JpaRepository<WorkCenter, Long> {
}
