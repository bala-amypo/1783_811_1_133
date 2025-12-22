package com.example.demo.service;

import com.example.demo.entity.InventoryLevel;
import java.util.List;

public interface InventoryLevelService
{
    InventoryLevel createInventory(InventoryLevel inventoryLevel);
    InventoryLevel getInventoryById(Long id);
    List<InventoryLevel> getAllInventories();
}
