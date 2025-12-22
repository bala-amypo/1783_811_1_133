package com.example.demo.serviceimpl;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.service.DemandForecastService;
import org.springframework.stereotype.Service;

@Service
public class DemandForecastServiceImpl implements DemandForecastService
{
    @Override
    public int forecastDemand(InventoryLevel inventoryLevel)
    {
        int currentStock = inventoryLevel.getQuantity();
        return (int) (currentStock * 1.2); // 20% growth forecast
    }
}
