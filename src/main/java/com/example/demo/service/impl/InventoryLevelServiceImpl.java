package com.example.demo.serviceimpl;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.service.InventoryLevelService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryLevelServiceImpl implements InventoryLevelService
{
    private final InventoryLevelRepository inventoryLevelRepository;

    public InventoryLevelServiceImpl(InventoryLevelRepository inventoryLevelRepository)
    {
        this.inventoryLevelRepository = inventoryLevelRepository;
    }

    public InventoryLevel createInventory(InventoryLevel inventoryLevel)
    {
        return inventoryLevelRepository.save(inventoryLevel);
    }

    public InventoryLevel getInventoryById(Long id)
    {
        return inventoryLevelRepository.findById(id).orElse(null);
    }

    public List<InventoryLevel> getAllInventories()
    {
        return inventoryLevelRepository.findAll();
    }
}
