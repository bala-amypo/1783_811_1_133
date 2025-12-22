package com.example.demo.service;

import com.example.demo.Entity.InventoryLevel;
import java.util.List;
import java.util.Optional;

public interface InventoryLevelService {
    List<InventoryLevel> getAllInventoryLevels();
    Optional<InventoryLevel> getInventoryLevelById(Long id);
    InventoryLevel saveInventoryLevel(InventoryLevel inventoryLevel);
    void deleteInventoryLevel(Long id);
}
