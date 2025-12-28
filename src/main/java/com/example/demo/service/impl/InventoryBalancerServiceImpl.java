package com.example.demo.service.impl;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.entity.Product;
import com.example.demo.entity.TransferSuggestion;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.TransferSuggestionRepository;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class InventoryBalancerServiceImpl implements InventoryBalancerService {

    private final ProductRepository productRepository;
    private final InventoryLevelRepository inventoryLevelRepository;
    private final TransferSuggestionRepository transferSuggestionRepository;

    public InventoryBalancerServiceImpl(
            ProductRepository productRepository,
            InventoryLevelRepository inventoryLevelRepository,
            TransferSuggestionRepository transferSuggestionRepository
    ) {
        this.productRepository = productRepository;
        this.inventoryLevelRepository = inventoryLevelRepository;
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

        if (inventory.size() < 2) {
            return List.of();
        }

        // ✅ Find highest and lowest stock
        InventoryLevel source = inventory.stream()
                .max(Comparator.comparingInt(InventoryLevel::getQuantity))
                .get();

        InventoryLevel target = inventory.stream()
                .min(Comparator.comparingInt(InventoryLevel::getQuantity))
                .get();

        if (source.getQuantity() <= target.getQuantity()) {
            return List.of();
        }

        TransferSuggestion suggestion = new TransferSuggestion();
        suggestion.setProduct(product);
        suggestion.setSourceStore(source.getStore());
        suggestion.setTargetStore(target.getStore());
        suggestion.setSuggestedQuantity(
                Math.max(1, (source.getQuantity() - target.getQuantity()) / 2)
        );
        suggestion.setReason("Auto-balancing based on inventory difference");

        transferSuggestionRepository.save(suggestion);

        return List.of(suggestion);
    }

    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return transferSuggestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}
