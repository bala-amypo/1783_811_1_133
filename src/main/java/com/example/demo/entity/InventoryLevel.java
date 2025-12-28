package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "inventory_levels",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"store_id", "product_id"})
    }
)
public class InventoryLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
}
