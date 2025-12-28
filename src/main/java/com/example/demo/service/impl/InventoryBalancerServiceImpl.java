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

    private final TransferSuggestionRepository transferRepo;
    private final InventoryLevelRepository inventoryRepo;
    private final DemandForecastRepository forecastRepo;
    private final ProductRepository productRepo;

    public InventoryBalancerServiceImpl(
            TransferSuggestionRepository transferRepo,
            InventoryLevelRepository inventoryRepo,
            DemandForecastRepository forecastRepo,
            ProductRepository productRepo
    ) {
        this.transferRepo = transferRepo;
        this.inventoryRepo = inventoryRepo;
        this.forecastRepo = forecastRepo;
        this.productRepo = productRepo;
    }

    @Override
    public List<TransferSuggestion> generateSuggestions(Long productId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (!product.isActive()) {
            throw new BadRequestException("Inactive product");
        }

        List<InventoryLevel> levels = inventoryRepo.findByProduct_Id(productId);
        if (levels.size() < 2) return List.of();

        InventoryLevel source = levels.get(0);
        InventoryLevel target = levels.get(1);

        if (source.getQuantity() < target.getQuantity()) {
            InventoryLevel tmp = source;
            source = target;
            target = tmp;
        }

        TransferSuggestion suggestion = new TransferSuggestion();
        suggestion.setProduct(product);
        suggestion.setSourceStore(source.getStore());
        suggestion.setTargetStore(target.getStore());
        suggestion.setSuggestedQuantity(
                Math.max(1, (source.getQuantity() - target.getQuantity()) / 2)
        );
        suggestion.setReason("Auto-generated");

        transferRepo.save(suggestion);
        return List.of(suggestion);
    }

    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return transferRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}
