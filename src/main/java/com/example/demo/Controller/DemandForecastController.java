package com.example.demo.controller;

import com.example.demo.service.DemandForecastService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forecast")
public class DemandForecastController
{
    private final DemandForecastService forecastService;

    public DemandForecastController(DemandForecastService forecastService)
    {
        this.forecastService = forecastService;
    }

    @GetMapping("/{storeId}/{productId}")
    public int getForecast(
            @PathVariable Long storeId,
            @PathVariable Long productId)
    {
        return forecastService.getForecast(storeId, productId);
    }
}
