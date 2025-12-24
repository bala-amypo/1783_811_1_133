package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer_suggestions")
public class TransferSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Store sourceStore;

    @ManyToOne(optional = false)
    private Store targetStore;

    @ManyToOne(optional = false)
    private Product product;

    private Integer quantity;

    private String priority;

    private String status = "PENDING";

    private LocalDateTime suggestedAt = LocalDateTime.now();

    public TransferSuggestion() {}

    public Long getId() {
        return id;
    }

    public Store getSourceStore() {
        return sourceStore;
    }

    public void setSourceStore(Store sourceStore) {
        this.sourceStore = sourceStore;
    }

    public Store getTargetStore() {
        return targetStore;
    }

    public void setTargetStore(Store targetStore) {
        this.targetStore = targetStore;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSuggestedAt() {
        return suggestedAt;
    }
}
