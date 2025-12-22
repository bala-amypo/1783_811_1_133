package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.service.InventoryLevelService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryLevelController
{
    private final InventoryLevelService inventoryLevelService;

    public InventoryLevelController(InventoryLevelService inventoryLevelService)
    {
        this.inventoryLevelService = inventoryLevelService;
    }

    @PostMapping
    public InventoryLevel createInventory(@RequestBody InventoryLevel inventoryLevel)
    {
        return inventoryLevelService.createInventory(inventoryLevel);
    }

    @GetMapping("/{id}")
    public InventoryLevel getInventory(@PathVariable Long id)
    {
        return inventoryLevelService.getInventoryById(id);
    }

    @GetMapping
    public List<InventoryLevel> getAllInventories()
    {
        return inventoryLevelService.getAllInventories();
    }
}
