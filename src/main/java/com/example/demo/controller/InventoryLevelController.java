package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.repository.InventoryLevelRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-levels")
public class InventoryLevelController {

    private final InventoryLevelRepository inventoryLevelRepository;

    public InventoryLevelController(InventoryLevelRepository inventoryLevelRepository) {
        this.inventoryLevelRepository = inventoryLevelRepository;
    }

    // Create / Update inventory level
    @PostMapping
    public InventoryLevel save(@RequestBody InventoryLevel inventoryLevel) {
        return inventoryLevelRepository.save(inventoryLevel);
    }

    // Get all inventory levels
    @GetMapping
    public List<InventoryLevel> getAll() {
        return inventoryLevelRepository.findAll();
    }

    // Get inventory level by id
    @GetMapping("/{id}")
    public InventoryLevel getById(@PathVariable Long id) {
        return inventoryLevelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory level not found"));
    }
}
