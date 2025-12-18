package com.example.demo.repository;

import com.example.demo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryLevelRepository extends JpaRepository<InventoryLevel, Long> {
    InventoryLevel findByStoreAndProduct(Store store, Product product);
}
