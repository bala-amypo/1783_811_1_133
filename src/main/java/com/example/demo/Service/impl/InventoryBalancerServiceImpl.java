package com.example.demo.serviceimpl;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryBalancerServiceImpl implements InventoryBalancerService
{
    private final InventoryLevelRepository inventoryLevelRepository;

    public InventoryBalancerServiceImpl(InventoryLevelRepository inventoryLevelRepository)
    {
        this.inventoryLevelRepository = inventoryLevelRepository;
    }

    @Override
    public String balanceInventory(Long productId)
    {
        List<InventoryLevel> inventories =
                inventoryLevelRepository.findByProductId(productId);

        if (inventories.size() < 2)
        {
            return "Not enough stores to balance inventory";
        }

        InventoryLevel source = inventories.get(0);
        InventoryLevel destination = inventories.get(1);

        if (source.getQuantity() > destination.getQuantity())
        {
            return "Transfer suggested from Store "
                    + source.getStore().getStoreName()
                    + " to Store "
                    + destination.getStore().getStoreName();
        }

        return "Inventory is already balanced";
    }
}
