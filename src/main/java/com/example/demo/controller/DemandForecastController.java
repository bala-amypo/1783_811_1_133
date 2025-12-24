package com.example.demo.controller;

import com.example.demo.entity.DemandForecast;
import com.example.demo.repository.DemandForecastRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forecasts")
public class DemandForecastController {

    private final DemandForecastRepository forecastRepository;

    public DemandForecastController(DemandForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    @PostMapping
    public DemandForecast create(@RequestBody DemandForecast forecast) {
        return forecastRepository.save(forecast);
    }

    @GetMapping
    public List<DemandForecast> getAll() {
        return forecastRepository.findAll();
    }
}
