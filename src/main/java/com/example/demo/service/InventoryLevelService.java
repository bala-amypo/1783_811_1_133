package com.example.demo.service;

import com.example.demo.entity.InventoryLevel;

import java.util.List;

public interface InventoryLevelService {

    InventoryLevel updateInventory(Long storeId, Long productId, Integer quantity);

    InventoryLevel getInventory(Long storeId, Long productId);

    List<InventoryLevel> getInventoryByStore(Long storeId);
}
