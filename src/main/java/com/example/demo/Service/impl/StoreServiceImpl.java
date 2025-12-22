package com.example.demo.service.impl;

import com.example.demo.Entity.Store;
import com.example.demo.Repository.StoreRepository;
import com.example.demo.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository repository;

    @Autowired
    public StoreServiceImpl(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Store> getAllStores() {
        return repository.findAll();
    }

    @Override
    public Optional<Store> getStoreById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Store saveStore(Store store) {
        return repository.save(store);
    }

    @Override
    public void deleteStore(Long id) {
        repository.deleteById(id);
    }
}
