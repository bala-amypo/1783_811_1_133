package com.example.demo.controller;

import com.example.demo.entity.TransferSuggestion;
import com.example.demo.repository.TransferSuggestionRepository;
import com.example.demo.service.InventoryBalancerServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferSuggestionController {

    private final InventoryBalancerServiceImpl balancerService;
    private final TransferSuggestionRepository transferRepository;

    public TransferSuggestionController(
            InventoryBalancerServiceImpl balancerService,
            TransferSuggestionRepository transferRepository) {
        this.balancerService = balancerService;
        this.transferRepository = transferRepository;
    }

    @PostMapping("/generate/{productId}")
    public String generate(@PathVariable Long productId) {
        balancerService.generateSuggestions(productId);
        return "Transfer suggestions generated";
    }

    @GetMapping
    public List<TransferSuggestion> getAll() {
        return transferRepository.findAll();
    }
}
