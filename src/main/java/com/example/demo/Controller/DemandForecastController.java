package com.example.demo.controller;

import com.example.demo.entity.DemandForecast;
import com.example.demo.service.DemandForecastService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forecasts")
public class DemandForecastController {

    private final DemandForecastService forecastService;

    public DemandForecastController(DemandForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @PostMapping
    public DemandForecast createForecast(@RequestBody DemandForecast forecast) {
        return forecastService.createForecast(forecast);
    }

    @GetMapping("/store/{storeId}/product/{productId}")
    public DemandForecast getForecast(
            @PathVariable Long storeId,
            @PathVariable Long productId) {
        return forecastService.getForecast(storeId, productId);
    }
}
