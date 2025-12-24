package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryLevelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryLevelServiceImpl implements InventoryLevelService {

    private final InventoryLevelRepository inventoryRepo;
    private final StoreRepository storeRepo;
    private final ProductRepository productRepo;

    public InventoryLevelServiceImpl(
            InventoryLevelRepository inventoryRepo,
            StoreRepository storeRepo,
            ProductRepository productRepo) {
        this.inventoryRepo = inventoryRepo;
        this.storeRepo = storeRepo;
        this.productRepo = productRepo;
    }

    @Override
    public InventoryLevel updateInventory(Long storeId, Long productId, Integer quantity) {

        if (quantity < 0) {
            throw new BadRequestException("quantity must be >= 0");
        }

        Store store = storeRepo.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        InventoryLevel inventory = inventoryRepo
                .findByStoreAndProduct(store, product)
                .orElse(new InventoryLevel());

        inventory.setStore(store);
        inventory.setProduct(product);
        inventory.setQuantity(quantity);

        return inventoryRepo.save(inventory);
    }

    @Override
    public InventoryLevel getInventory(Long storeId, Long productId) {

        Store store = storeRepo.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return inventoryRepo.findByStoreAndProduct(store, product)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found"));
    }

    @Override
    public List<InventoryLevel> getInventoryByStore(Long storeId) {

        Store store = storeRepo.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        return inventoryRepo.findByStore(store);
    }
}
