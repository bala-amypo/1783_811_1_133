package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.repository.InventoryLevelRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryLevelController {

    private final InventoryLevelService inventoryLevelService;

    public InventoryLevelController(InventoryLevelService inventoryLevelService) {
        this.inventoryLevelService = inventoryLevelService;
    }

    @PostMapping
    public InventoryLevel createOrUpdate(@RequestBody InventoryLevel inventoryLevel) {
        return inventoryLevelService.createOrUpdateInventory(inventoryLevel);
    }

    @GetMapping
    public List<InventoryLevel> getAll() {
        return inventoryLevelService.getAllInventory();
    }
}
