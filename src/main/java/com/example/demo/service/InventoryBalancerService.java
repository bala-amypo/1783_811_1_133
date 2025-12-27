package com.example.demo.service.impl;

import com.example.demo.entity.TransferSuggestion;
import com.example.demo.entity.InventoryLevel;
import com.example.demo.entity.DemandForecast;
import com.example.demo.entity.Store;
import com.example.demo.entity.Product;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryBalancerServiceImpl implements InventoryBalancerService {

    private final TransferSuggestionRepository transferSuggestionRepository;
    private final InventoryLevelRepository inventoryLevelRepository;
    private final DemandForecastRepository demandForecastRepository;
    private final ProductRepository productRepository;

    public InventoryBalancerServiceImpl(
            TransferSuggestionRepository transferSuggestionRepository,
            InventoryLevelRepository inventoryLevelRepository,
            DemandForecastRepository demandForecastRepository,
            ProductRepository productRepository
    ) {
        this.transferSuggestionRepository = transferSuggestionRepository;
        this.inventoryLevelRepository = inventoryLevelRepository;
        this.demandForecastRepository = demandForecastRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<TransferSuggestion> generateSuggestions(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (!product.isActive()) {
            throw new BadRequestException("Inactive product");
        }

        // 1. Get all inventory levels and forecasts for this product
        List<InventoryLevel> inventoryLevels = inventoryLevelRepository.findByProduct_Id(productId);
        List<DemandForecast> forecasts = demandForecastRepository.findByProduct_Id(productId);

        // 2. Map them by Store ID for easy lookup
        Map<Long, Integer> inventoryMap = inventoryLevels.stream()
                .collect(Collectors.toMap(
                        inv -> inv.getStore().getId(),
                        InventoryLevel::getQuantity
                ));

        Map<Long, Integer> forecastMap = forecasts.stream()
                .collect(Collectors.toMap(
                        fc -> fc.getStore().getId(),
                        DemandForecast::getForecastedDemand
                ));

        // 3. Identify Surplus and Deficit stores
        // We'll track the "available surplus" and "needed deficit" as mutable objects or simple maps
        List<Store> allStores = inventoryLevels.stream().map(InventoryLevel::getStore).collect(Collectors.toList());
        // Add stores from forecasts that might not be in inventory list (though rare in this domain model, safe to include)
        forecasts.forEach(fc -> {
            if (allStores.stream().noneMatch(s -> s.getId().equals(fc.getStore().getId()))) {
                allStores.add(fc.getStore());
            }
        });

        // Use distinct stores
        Map<Long, Store> storeMap = allStores.stream()
                .distinct()
                .collect(Collectors.toMap(Store::getId, s -> s));

        List<AbstractMap.SimpleEntry<Long, Integer>> surplusStores = new ArrayList<>();
        List<AbstractMap.SimpleEntry<Long, Integer>> deficitStores = new ArrayList<>();

        for (Long storeId : storeMap.keySet()) {
            int inv = inventoryMap.getOrDefault(storeId, 0);
            int demand = forecastMap.getOrDefault(storeId, 0);

            if (inv > demand) {
                surplusStores.add(new AbstractMap.SimpleEntry<>(storeId, inv - demand));
            } else if (inv < demand) {
                deficitStores.add(new AbstractMap.SimpleEntry<>(storeId, demand - inv));
            }
        }

        // 4. Generate Suggestions via greedy matching
        List<TransferSuggestion> suggestions = new ArrayList<>();

        int surplusIdx = 0;
        int deficitIdx = 0;

        while (surplusIdx < surplusStores.size() && deficitIdx < deficitStores.size()) {
            Map.Entry<Long, Integer> surplus = surplusStores.get(surplusIdx);
            Map.Entry<Long, Integer> deficit = deficitStores.get(deficitIdx);

            int amountToTransfer = Math.min(surplus.getValue(), deficit.getValue());

            if (amountToTransfer > 0) {
                TransferSuggestion suggestion = new TransferSuggestion();
                suggestion.setProduct(product);
                suggestion.setSourceStore(storeMap.get(surplus.getKey()));
                suggestion.setTargetStore(storeMap.get(deficit.getKey()));
                suggestion.setSuggestedQuantity(amountToTransfer);
                suggestion.setReason("Balancing inventory: Surplus at " + surplus.getKey() + ", Deficit at " + deficit.getKey());
                
                suggestions.add(transferSuggestionRepository.save(suggestion));
            }

            // Update remaining amounts
            int surplusRemaining = surplus.getValue() - amountToTransfer;
            int deficitRemaining = deficit.getValue() - amountToTransfer;

            if (surplusRemaining == 0) {
                surplusIdx++;
            } else {
                surplusStores.set(surplusIdx, new AbstractMap.SimpleEntry<>(surplus.getKey(), surplusRemaining));
            }

            if (deficitRemaining == 0) {
                deficitIdx++;
            } else {
                deficitStores.set(deficitIdx, new AbstractMap.SimpleEntry<>(deficit.getKey(), deficitRemaining));
            }
        }

        return suggestions;
    }

    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return transferSuggestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}
