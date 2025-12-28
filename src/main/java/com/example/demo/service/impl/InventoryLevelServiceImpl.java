package com.example.demo.service.impl;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.service.InventoryLevelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryLevelServiceImpl implements InventoryLevelService {

    private final InventoryLevelRepository inventoryRepo;

    public InventoryLevelServiceImpl(InventoryLevelRepository inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    @Override
    public InventoryLevel createOrUpdateInventory(InventoryLevel inventoryLevel) {

        // ✅ Required validations (tests depend on this)
        if (inventoryLevel.getStore() == null || inventoryLevel.getProduct() == null) {
            throw new BadRequestException("Store and Product are required");
        }

        if (inventoryLevel.getQuantity() == null || inventoryLevel.getQuantity() < 0) {
            throw new BadRequestException("Quantity cannot be negative");
        }

        // ✅ TRUE UPSERT logic (store + product uniqueness)
        InventoryLevel existing =
                inventoryRepo.findByStore_IdAndProduct_Id(
                        inventoryLevel.getStore().getId(),
                        inventoryLevel.getProduct().getId()
                );

        if (existing != null) {
            // update same managed entity
            existing.setQuantity(inventoryLevel.getQuantity());
            return inventoryRepo.save(existing);
        }

        // insert new row
        return inventoryRepo.save(inventoryLevel);
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
