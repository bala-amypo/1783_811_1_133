package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.repository.InventoryLevelRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryLevelRepository inventoryRepository;

    public InventoryController(InventoryLevelRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PostMapping
    public InventoryLevel add(@RequestBody InventoryLevel inventory) {
        return inventoryRepository.save(inventory);
    }

    @GetMapping
    public List<InventoryLevel> getAll() {
        return inventoryRepository.findAll();
    }
}
