package com.example.demo.Repository;

import com.example.demo.Entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository
        extends JpaRepository<Store, Long>
{
}
