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
    private LocalDate forecastDate;

    @Column(nullable = false)
    private Integer forecastedDemand;

    private Double confidenceScore;

    // REQUIRED by JPA
    public DemandForecast() {}

    // ---------- GETTERS ----------
    public Long getId() {
        return id;
    }

    public Store getStore() {
        return store;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDate getForecastDate() {
        return forecastDate;
    }

    public Integer getForecastedDemand() {
        return forecastedDemand;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    // ---------- SETTERS ----------
    public void setStore(Store store) {
        this.store = store;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setForecastDate(LocalDate forecastDate) {
        this.forecastDate = forecastDate;
    }

    public void setForecastedDemand(Integer forecastedDemand) {
        this.forecastedDemand = forecastedDemand;
    }

    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }
}
