package com.example.demo.repository;

import com.example.demo.entity.InventoryLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryLevelRepository extends JpaRepository<InventoryLevel, Long>
{
    InventoryLevel findByStoreIdAndProductId(Long storeId, Long productId);
}
