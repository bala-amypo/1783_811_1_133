package com.example.demo.service.impl;

import com.example.demo.Entity.DemandForecast;
import com.example.demo.Repository.DemandForecastRepository;
import com.example.demo.service.DemandForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemandForecastServiceImpl implements DemandForecastService {

    private final DemandForecastRepository repository;

    @Autowired
    public DemandForecastServiceImpl(DemandForecastRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DemandForecast> getAllForecasts() {
        return repository.findAll();
    }

    @Override
    public Optional<DemandForecast> getForecastById(Long id) {
        return repository.findById(id);
    }

    @Override
    public DemandForecast saveForecast(DemandForecast forecast) {
        return repository.save(forecast);
    }

    @Override
    public void deleteForecast(Long id) {
        repository.deleteById(id);
    }
}
