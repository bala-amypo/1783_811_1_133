package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
public class TransferSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromStore;
    private String toStore;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFromStore() { return fromStore; }
    public void setFromStore(String fromStore) { this.fromStore = fromStore; }

    public String getToStore() { return toStore; }
    public void setToStore(String toStore) { this.toStore = toStore; }
}
