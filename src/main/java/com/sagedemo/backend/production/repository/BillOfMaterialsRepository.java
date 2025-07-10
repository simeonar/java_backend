package com.sagedemo.backend.production.repository;

import com.sagedemo.backend.production.entity.BillOfMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillOfMaterialsRepository extends JpaRepository<BillOfMaterials, Long> {
}
