package com.example.demo.Repository;

import com.example.demo.Entity.DemandForecast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandForecastRepository
        extends JpaRepository<DemandForecast, Long>
{
}
