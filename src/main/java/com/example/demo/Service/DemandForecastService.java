package com.example.demo.service;

import com.example.demo.entity.DemandForecast;
import com.example.demo.repository.DemandForecastRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DemandForecastService {

    private final DemandForecastRepository forecastRepository;

    public DemandForecastService(DemandForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    public DemandForecast createForecast(DemandForecast forecast) {
        if (forecast.getForecastDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Forecast date must be in the future");
        }
        return forecastRepository.save(forecast);
    }

    public DemandForecast getForecast(Long storeId, Long productId) {
        return forecastRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No forecast found"));
    }
}
