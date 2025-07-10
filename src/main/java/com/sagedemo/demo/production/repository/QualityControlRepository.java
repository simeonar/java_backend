package com.sagedemo.demo.production.repository;

import com.sagedemo.demo.production.entity.QualityControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualityControlRepository extends JpaRepository<QualityControl, Long> {
}
