package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "demand_forecasts")
public class DemandForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Store store;

    @ManyToOne(optional = false)
    private Product product;

    @Column(nullable = false)
    private Integer forecastedDemand;

    @Column(nullable = false)
    private LocalDate forecastDate;

    @PrePersist
    public void validateFutureDate() {
        if (forecastDate.isBefore(LocalDate.now())) {
            throw new BadRequestException("Forecast date must be in the future");
        }
    }

    // getters & setters
}
