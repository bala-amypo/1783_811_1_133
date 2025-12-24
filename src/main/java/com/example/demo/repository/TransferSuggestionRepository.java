package com.example.demo.repository;

import com.example.demo.Entity.TransferSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferSuggestionRepository
        extends JpaRepository<TransferSuggestion, Long>
{
}
