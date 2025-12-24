package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
public class TransferSuggestionController {

    @GetMapping("/suggest")
    public String suggest() {
        return "Transfer suggestion generated";
    }
}
