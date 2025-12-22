package com.example.demo.service;

import com.example.demo.entity.InventoryLevel;

public interface DemandForecastService
{
    int forecastDemand(InventoryLevel inventoryLevel);
}
