package com.example.demo.Repository;

import com.example.demo.Entity.InventoryLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryLevelRepository
        extends JpaRepository<InventoryLevel, Long>
{
}
