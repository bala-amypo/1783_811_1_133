package com.example.demo.service;

import com.example.demo.Entity.Store;
import java.util.List;
import java.util.Optional;

public interface StoreService {
    List<Store> getAllStores();
    Optional<Store> getStoreById(Long id);
    Store saveStore(Store store);
    void deleteStore(Long id);
}
