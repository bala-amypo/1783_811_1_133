package com.example.demo.service.impl;

import com.example.demo.entity.DemandForecast;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.DemandForecastRepository;
import com.example.demo.service.DemandForecastService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DemandForecastServiceImpl implements DemandForecastService {

    private final DemandForecastRepository forecastRepo;

    public DemandForecastServiceImpl(DemandForecastRepository forecastRepo) {
        this.forecastRepo = forecastRepo;
    }

    @Override
public DemandForecast createForecast(DemandForecast forecast) {

    if (forecast.getForecastDate().isBefore(LocalDate.now())) {
        throw new BadRequestException("Forecast date cannot be in the past");
    }

    return forecastRepo.save(forecast);
}


    @Override
    public List<DemandForecast> getForecastsForStore(Long storeId) {
        return forecastRepo.findByStore_Id(storeId);
    }
}
