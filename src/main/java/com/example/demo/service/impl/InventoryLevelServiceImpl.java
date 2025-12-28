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
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;


@Service
@Transactional
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

    Long storeId = inventoryLevel.getStore().getId();
    Long productId = inventoryLevel.getProduct().getId();

    if (storeId == null || productId == null) {
        throw new BadRequestException("Store ID and Product ID are required");
    }

    Store store = storeRepo.findById(storeId)
            .orElseThrow(() -> new BadRequestException("Store not found"));

    Product product = productRepo.findById(productId)
            .orElseThrow(() -> new BadRequestException("Product not found"));

    Optional<InventoryLevel> existing =
            inventoryRepo.findByStore_IdAndProduct_Id(storeId, productId);

    if (existing.isPresent()) {
        InventoryLevel entity = existing.get();
        entity.setQuantity(inventoryLevel.getQuantity());
        // ensure update timestamp
        entity.setQuantity(inventoryLevel.getQuantity());
        return inventoryRepo.save(entity);
    }

    InventoryLevel entity = new InventoryLevel();
    entity.setStore(store);
    entity.setProduct(product);
    entity.setQuantity(inventoryLevel.getQuantity());

    return inventoryRepo.save(entity);
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
