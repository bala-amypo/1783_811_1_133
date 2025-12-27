package com.example.demo.service.impl;

import com.example.demo.entity.TransferSuggestion;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InventoryBalancerServiceImpl implements InventoryBalancerService {

    private final ProductRepository productRepository;
    private final InventoryLevelRepository inventoryRepo;
    private final DemandForecastRepository forecastRepo;
    private final TransferSuggestionRepository suggestionRepo;

    public InventoryBalancerServiceImpl(
            ProductRepository productRepository,
            InventoryLevelRepository inventoryRepo,
            DemandForecastRepository forecastRepo,
            TransferSuggestionRepository suggestionRepo
    ) {
        this.productRepository = productRepository;
        this.inventoryRepo = inventoryRepo;
        this.forecastRepo = forecastRepo;
        this.suggestionRepo = suggestionRepo;
    }

    @Override
    public List<TransferSuggestion> generateSuggestions(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (!product.isActive()) {
            throw new BadRequestException("Inactive product");
        }

        List<InventoryLevel> inventory = inventoryRepo.findByProduct_Id(productId);
        List<DemandForecast> forecasts = forecastRepo.findByProduct_Id(productId);

        if (inventory.size() < 2 || forecasts.isEmpty()) {
            return List.of();
        }

        InventoryLevel over = inventory.get(0);
        InventoryLevel under = inventory.get(inventory.size() - 1);

        TransferSuggestion suggestion = new TransferSuggestion();
        suggestion.setProduct(product);
        suggestion.setSourceStore(over.getStore());
        suggestion.setTargetStore(under.getStore());
        suggestion.setSuggestedQuantity(10);
        suggestion.setReason("Auto-balanced");

        suggestionRepo.save(suggestion);

        return suggestionRepo.findByProduct_Id(productId);
    }

    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return suggestionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}
