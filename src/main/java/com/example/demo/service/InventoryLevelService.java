package com.example.demo.service;

import com.example.demo.entity.InventoryLevel;
import java.util.List;

@Override
public InventoryLevel createOrUpdateInventory(InventoryLevel inv) {

    if (inv.getQuantity() < 0) {
        throw new BadRequestException("Quantity cannot be negative");
    }

    return inventoryRepo
        .findByStore_IdAndProduct_Id(
            inv.getStore().getId(),
            inv.getProduct().getId()
        )
        .map(existing -> {
            existing.setQuantity(inv.getQuantity());
            return inventoryRepo.save(existing);
        })
        .orElse(inventoryRepo.save(inv));
}
