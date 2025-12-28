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

    private final ProductRepository productRepository;
    private final InventoryLevelRepository inventoryLevelRepository;
    private final DemandForecastRepository demandForecastRepository;
    private final TransferSuggestionRepository transferSuggestionRepository;

    public InventoryBalancerServiceImpl(
            ProductRepository productRepository,
            InventoryLevelRepository inventoryLevelRepository,
            DemandForecastRepository demandForecastRepository,
            TransferSuggestionRepository transferSuggestionRepository
    ) {
        this.productRepository = productRepository;
        this.inventoryLevelRepository = inventoryLevelRepository;
        this.demandForecastRepository = demandForecastRepository;
        this.transferSuggestionRepository = transferSuggestionRepository;
    }

    @Override
public List<TransferSuggestion> generateSuggestions(Long productId) {

    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    if (!product.isActive()) {
        throw new BadRequestException("Inactive product");
    }

    List<InventoryLevel> inventory =
            inventoryLevelRepository.findByProduct_Id(productId);

    List<DemandForecast> forecasts =
            demandForecastRepository.findByProduct_Id(productId);

    if (inventory.size() < 2 || forecasts.isEmpty()) {
        return List.of();
    }

    InventoryLevel high = inventory.get(0);
    InventoryLevel low = inventory.get(1);

    if (high.getQuantity() < low.getQuantity()) {
        InventoryLevel temp = high;
        high = low;
        low = temp;
    }

    TransferSuggestion ts = new TransferSuggestion();
    ts.setProduct(product);
    ts.setSourceStore(high.getStore());
    ts.setTargetStore(low.getStore());
    ts.setSuggestedQuantity(
            Math.max(1, (high.getQuantity() - low.getQuantity()) / 2)
    );
    ts.setReason("Auto-balancing");

    transferSuggestionRepository.save(ts);

    return List.of(ts);
}


    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return transferSuggestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}
