package com.sagedemo.demo.production.repository;

import com.sagedemo.demo.production.entity.BillOfMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillOfMaterialsRepository extends JpaRepository<BillOfMaterials, Long> {
}
