package com.example.demo.serviceimpl;

import com.example.demo.entity.Store;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.StoreService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService
{
    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository)
    {
        this.storeRepository = storeRepository;
    }

    public Store createStore(Store store)
    {
        return storeRepository.save(store);
    }

    public Store getStoreById(Long id)
    {
        return storeRepository.findById(id).orElse(null);
    }

    public List<Store> getAllStores()
    {
        return storeRepository.findAll();
    }
}
