package com.sagedemo.demo.production.repository;

import com.sagedemo.demo.production.entity.WorkCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkCenterRepository extends JpaRepository<WorkCenter, Long> {
}
