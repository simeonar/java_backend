package com.sagedemo.demo.purchasing.repository;

import com.sagedemo.demo.purchasing.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
