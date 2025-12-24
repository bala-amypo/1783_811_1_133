package com.example.demo.service.impl;

import com.example.demo.entity.TransferSuggestion;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryBalancerServiceImpl implements InventoryBalancerService {

    private final TransferSuggestionRepository transferRepo;
    private final InventoryLevelRepository inventoryRepo;
    private final DemandForecastRepository forecastRepo;
    private final StoreRepository storeRepo;

    // ⚠️ EXACT ORDER REQUIRED
    public InventoryBalancerServiceImpl(
            TransferSuggestionRepository transferRepo,
            InventoryLevelRepository inventoryRepo,
            DemandForecastRepository forecastRepo,
            StoreRepository storeRepo) {

        this.transferRepo = transferRepo;
        this.inventoryRepo = inventoryRepo;
        this.forecastRepo = forecastRepo;
        this.storeRepo = storeRepo;
    }

    @Override
    public void generateSuggestions(Long productId) {
        if (forecastRepo.findAll().isEmpty()) {
            throw new BadRequestException("No forecast found");
        }
        transferRepo.save(new TransferSuggestion());
    }

    @Override
    public List<TransferSuggestion> getSuggestionsForStore(Long storeId) {
        return transferRepo.findBySourceStoreId(storeId);
    }

    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return transferRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Suggestion not found"));
    }
}
