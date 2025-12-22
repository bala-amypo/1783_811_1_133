package com.example.demo.Controller;

import com.example.demo.Entity.Store;
import com.example.demo.Repository.StoreRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreRepository repo;

    public StoreController(StoreRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Store save(@RequestBody Store s) {
        return repo.save(s);
    }

    @GetMapping
    public List<Store> getAll() {
        return repo.findAll();
    }
}
