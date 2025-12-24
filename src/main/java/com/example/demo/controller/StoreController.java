package com.example.demo.controller;

import com.example.demo.entity.Store;
import com.example.demo.repository.StoreRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreRepository storeRepository;

    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @PostMapping
    public Store create(@RequestBody Store store) {
        return storeRepository.save(store);
    }

    @GetMapping
    public List<Store> getAll() {
        return storeRepository.findAll();
    }
}
