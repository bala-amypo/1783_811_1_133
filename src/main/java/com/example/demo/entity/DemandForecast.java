package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
public class DemandForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private int forecastQuantity;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getForecastQuantity() { return forecastQuantity; }
    public void setForecastQuantity(int forecastQuantity) { this.forecastQuantity = forecastQuantity; }
}
