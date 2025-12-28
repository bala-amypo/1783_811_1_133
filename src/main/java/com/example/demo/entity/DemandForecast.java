package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "demand_forecasts")
public class DemandForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private LocalDate forecastDate;

    @Column(nullable = false)
    private Integer forecastedDemand;

    public DemandForecast() {}

    public Long getId() { return id; }

    public Store getStore() { return store; }
    public void setStore(Store store) { this.store = store; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public LocalDate getForecastDate() { return forecastDate; }
    public void setForecastDate(LocalDate forecastDate) { this.forecastDate = forecastDate; }

    public Integer getForecastedDemand() { return forecastedDemand; }
    public void setForecastedDemand(Integer forecastedDemand) {
        this.forecastedDemand = forecastedDemand;
    }
}
