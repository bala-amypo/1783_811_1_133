package com.example.demo.Controller;

import com.example.demo.Entity.DemandForecast;
import com.example.demo.Repository.DemandForecastRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forecast")
public class DemandForecastController {

    private final DemandForecastRepository repo;

    public DemandForecastController(DemandForecastRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public DemandForecast save(@RequestBody DemandForecast forecast) {
        return repo.save(forecast);
    }

    @GetMapping("/{id}")
    public DemandForecast get(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }
}
