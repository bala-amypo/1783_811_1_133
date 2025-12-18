package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryLevelService {

    private final InventoryLevelRepository inventoryRepo;
    private final StoreRepository storeRepo;
    private final ProductRepository productRepo;

    public InventoryLevelService(
            InventoryLevelRepository inventoryRepo,
            StoreRepository storeRepo,
            ProductRepository productRepo) {
        this.inventoryRepo = inventoryRepo;
        this.storeRepo = storeRepo;
        this.productRepo = productRepo;
    }

    public InventoryLevel updateInventory(Long storeId, Long productId, Integer quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Quantity must be >= 0");

        Store store = storeRepo.findById(storeId).orElseThrow();
        Product product = productRepo.findById(productId).orElseThrow();

        InventoryLevel level =
                inventoryRepo.findByStoreAndProduct(store, product);

        if (level == null) {
            level = new InventoryLevel();
            level.setStore(store);
            level.setProduct(product);
        }

        level.setQuantity(quantity);
        return inventoryRepo.save(level);
    }

    public List<InventoryLevel> getInventoryByStore(Long storeId) {
        Store store = storeRepo.findById(storeId).orElseThrow();
        return inventoryRepo.findAll()
                .stream()
                .filter(i -> i.getStore().equals(store))
                .toList();
    }
}
