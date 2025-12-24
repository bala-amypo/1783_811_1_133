package com.example.demo.Controller;

import com.example.demo.Entity.InventoryLevel;
import com.example.demo.Repository.InventoryLevelRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryLevelController {

    private final InventoryLevelRepository repo;

    public InventoryLevelController(InventoryLevelRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public InventoryLevel save(@RequestBody InventoryLevel inv) {
        return repo.save(inv);
    }

    @GetMapping
    public List<InventoryLevel> getAll() {
        return repo.findAll();
    }
}
