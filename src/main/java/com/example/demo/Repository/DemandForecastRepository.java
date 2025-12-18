package com.example.demo.repository;

import com.example.demo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DemandForecastRepository extends JpaRepository<DemandForecast, Long> {
    DemandForecast findByStoreAndProductAndForecastDateAfter(
            Store store,
            Product product,
            LocalDate date
    );
}
