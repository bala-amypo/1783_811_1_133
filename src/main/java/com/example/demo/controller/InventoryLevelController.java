package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.service.InventoryLevelService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryLevelController {

    private final InventoryLevelService inventoryService;

    public InventoryLevelController(InventoryLevelService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public InventoryLevel create(@RequestBody InventoryLevel inv) {

        InventoryLevel safe = new InventoryLevel();
        safe.setQuantity(inv.getQuantity());

        Store store = new Store();
        store.setId(inv.getStore().getId());

        Product product = new Product();
        product.setId(inv.getProduct().getId());

        safe.setStore(store);
        safe.setProduct(product);

        return inventoryService.createOrUpdateInventory(safe);
    }
}
