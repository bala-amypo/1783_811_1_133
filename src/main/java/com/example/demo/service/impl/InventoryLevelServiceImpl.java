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

        if (inventoryLevel.getQuantity() < 0) {
            throw new BadRequestException("Quantity cannot be negative");
        }

        return inventoryRepo
            .findByStore_IdAndProduct_Id(
                inventoryLevel.getStore().getId(),
                inventoryLevel.getProduct().getId()
            )
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
