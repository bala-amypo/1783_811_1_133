package com.example.demo.service;

import com.example.demo.entity.TransferSuggestion;
import com.example.demo.repository.TransferSuggestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryBalancerService {

    private final TransferSuggestionRepository repo;

    public InventoryBalancerService(TransferSuggestionRepository repo) {
        this.repo = repo;
    }

    public List<TransferSuggestion> generateSuggestions(Long productId) {
        return repo.findAll();
    }

    public List<TransferSuggestion> getSuggestionsForStore(Long storeId) {
        return repo.findBySourceStoreId(storeId);
    }

    public TransferSuggestion getSuggestionById(Long id) {
        return repo.findById(id).orElseThrow();
    }
}
