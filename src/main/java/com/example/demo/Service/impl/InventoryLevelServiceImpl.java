package com.example.demo.service.impl;

import com.example.demo.Entity.InventoryLevel;
import com.example.demo.Repository.InventoryLevelRepository;
import com.example.demo.service.InventoryLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryLevelServiceImpl implements InventoryLevelService {

    private final InventoryLevelRepository repository;

    @Autowired
    public InventoryLevelServiceImpl(InventoryLevelRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<InventoryLevel> getAllInventoryLevels() {
        return repository.findAll();
    }

    @Override
    public Optional<InventoryLevel> getInventoryLevelById(Long id) {
        return repository.findById(id);
    }

    @Override
    public InventoryLevel saveInventoryLevel(InventoryLevel inventoryLevel) {
        return repository.save(inventoryLevel);
    }

    @Override
    public void deleteInventoryLevel(Long id) {
        repository.deleteById(id);
    }
}
