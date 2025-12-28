package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.service.InventoryLevelService;
import com.example.demo.service.ProductService;
import com.example.demo.service.StoreService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryLevelController {

    private final InventoryLevelService inventoryService;
    private final StoreService storeService;
    private final ProductService productService;

    public InventoryLevelController(
            InventoryLevelService inventoryService,
            StoreService storeService,
            ProductService productService
    ) {
        this.inventoryService = inventoryService;
        this.storeService = storeService;
        this.productService = productService;
    }

    @PostMapping
    public InventoryLevel save(@RequestBody InventoryLevel inventoryLevel) {

        Store store = storeService.getStoreById(
                inventoryLevel.getStore().getId()
        );

        Product product = productService.getProductById(
                inventoryLevel.getProduct().getId()
        );

        inventoryLevel.setStore(store);
        inventoryLevel.setProduct(product);

        return inventoryService.createOrUpdateInventory(inventoryLevel);
    }
}
