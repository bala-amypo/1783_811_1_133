package com.example.demo.serviceimpl;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.service.DemandForecastService;
import org.springframework.stereotype.Service;

@Service
public class DemandForecastServiceImpl implements DemandForecastService
{
    private final InventoryLevelRepository inventoryLevelRepository;

    public DemandForecastServiceImpl(InventoryLevelRepository inventoryLevelRepository)
    {
        this.inventoryLevelRepository = inventoryLevelRepository;
    }

    @Override
    public int getForecast(Long storeId, Long productId)
    {
        InventoryLevel inventory =
                inventoryLevelRepository.findByStoreIdAndProductId(storeId, productId);

        if (inventory == null)
        {
            return 0;
        }

        return (int) (inventory.getQuantity() * 1.2); // 20% growth
    }
}
