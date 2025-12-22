package com.example.demo.controller;

import com.example.demo.service.InventoryBalancerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
public class TransferSuggestionController
{
    private final InventoryBalancerService inventoryBalancerService;

    public TransferSuggestionController(InventoryBalancerService inventoryBalancerService)
    {
        this.inventoryBalancerService = inventoryBalancerService;
    }

    @GetMapping("/suggest/{productId}")
    public String suggestTransfer(@PathVariable Long productId)
    {
        return inventoryBalancerService.balanceInventory(productId);
    }
}
