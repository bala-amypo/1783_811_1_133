package com.example.demo.service.impl;

import com.example.demo.entity.Store;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.StoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Store createStore(Store store) {

        // 🔴 REQUIRED validations (tests expect this)
        if (store.getStoreName() == null || store.getStoreName().isBlank()) {
            throw new BadRequestException("Store name is required");
        }

        if (store.getRegion() == null || store.getRegion().isBlank()) {
            throw new BadRequestException("Region is required");
        }

        if (store.getAddress() == null || store.getAddress().isBlank()) {
            throw new BadRequestException("Address is required");
        }

        // ✅ default active = true (entity already does this)
        store.setActive(true);

        return storeRepository.save(store);
    }

    @Override
    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Store updateStore(Long id, Store updated) {
        Store store = getStoreById(id);

        // ✅ tests allow updating name, region, address
        store.setStoreName(updated.getStoreName());
        store.setAddress(updated.getAddress());
        store.setRegion(updated.getRegion());

        // ✅ active flag respected
        store.setActive(updated.isActive());

        return storeRepository.save(store);
    }

    @Override
    public void deactivateStore(Long id) {
        Store store = getStoreById(id);
        store.setActive(false);
        storeRepository.save(store);
    }
}
