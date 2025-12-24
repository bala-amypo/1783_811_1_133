package com.example.demo.controller;

import com.example.demo.service.InventoryBalancerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferSuggestionController {

    private final InventoryBalancerService balancerService;

    public TransferSuggestionController(InventoryBalancerService balancerService) {
        this.balancerService = balancerService;
    }

    @PostMapping("/generate/{productId}")
    public String generate(@PathVariable Long productId) {
        balancerService.generateSuggestions(productId);
        return "Transfer suggestions generated";
    }
}
