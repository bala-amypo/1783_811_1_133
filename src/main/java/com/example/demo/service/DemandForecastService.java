package com.example.demo.service;

import com.example.demo.entity.DemandForecast;
import java.util.List;
import java.util.Optional;

public interface DemandForecastService {
    List<DemandForecast> getAllForecasts();
    Optional<DemandForecast> getForecastById(Long id);
    DemandForecast saveForecast(DemandForecast forecast);
    void deleteForecast(Long id);
}
