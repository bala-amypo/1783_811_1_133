package com.example.demo.service.impl;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
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
            ProductRepository productRepo
    ) {
        this.inventoryRepo = inventoryRepo;
        this.storeRepo = storeRepo;
        this.productRepo = productRepo;
    }

    @Override
    public InventoryLevel createOrUpdateInventory(InventoryLevel inventoryLevel) {

        if (inventoryLevel.getStore() == null || inventoryLevel.getProduct() == null) {
            throw new BadRequestException("Store and Product are required");
        }

        if (inventoryLevel.getQuantity() == null || inventoryLevel.getQuantity() < 0) {
            throw new BadRequestException("Quantity cannot be negative");
        }

        // 🔑 REATTACH managed entities
        Store store = storeRepo.findById(inventoryLevel.getStore().getId())
                .orElseThrow(() -> new BadRequestException("Store not found"));

        Product product = productRepo.findById(inventoryLevel.getProduct().getId())
                .orElseThrow(() -> new BadRequestException("Product not found"));

        inventoryLevel.setStore(store);
        inventoryLevel.setProduct(product);

        return inventoryRepo
                .findByStore_IdAndProduct_Id(store.getId(), product.getId())
                .map(existing -> {
                    existing.setQuantity(inventoryLevel.getQuantity());
                    return inventoryRepo.save(existing);
                })
                .orElseGet(() -> inventoryRepo.save(inventoryLevel));
    }

    @Override
    public List<InventoryLevel> getInventoryForStore(Long storeId) {
        return inventoryRepo.findByStore_Id(storeId);
    }

    @Override
    public List<InventoryLevel> getInventoryForProduct(Long productId) {
        return inventoryRepo.findByProduct_Id(productId);
    }
}
