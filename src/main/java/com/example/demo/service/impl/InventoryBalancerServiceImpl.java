package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        List<InventoryLevel> inventoryLevels =
                inventoryLevelRepository.findByProduct_Id(productId);

        List<DemandForecast> forecasts =
                demandForecastRepository.findByProduct_Id(productId);

        if (inventoryLevels.size() < 2 || forecasts.isEmpty()) {
            return new ArrayList<>();
        }

        InventoryLevel overStock = inventoryLevels.get(0);
        InventoryLevel underStock = inventoryLevels.get(1);

        if (overStock.getQuantity() < underStock.getQuantity()) {
            InventoryLevel temp = overStock;
            overStock = underStock;
            underStock = temp;
        }

        TransferSuggestion suggestion = new TransferSuggestion();
        suggestion.setProduct(product);
        suggestion.setSourceStore(overStock.getStore());
        suggestion.setTargetStore(underStock.getStore());
        suggestion.setSuggestedQuantity(
                Math.max(1, (overStock.getQuantity() - underStock.getQuantity()) / 2)
        );
        suggestion.setReason("Auto-balancing based on demand forecast");

        transferSuggestionRepository.save(suggestion);

        return List.of(suggestion);
    }

    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return transferSuggestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}
