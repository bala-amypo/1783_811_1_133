package com.example.demo.service.impl;

import com.example.demo.entity.Product;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {

        // 🔴 REQUIRED validations (tests expect this)
        if (product.getSku() == null || product.getSku().isBlank()) {
            throw new BadRequestException("SKU is required");
        }

        if (product.getName() == null || product.getName().isBlank()) {
            throw new BadRequestException("Product name is required");
        }

        if (productRepository.findBySku(product.getSku()).isPresent()) {
            throw new BadRequestException("SKU already exists");
        }

        // ✅ DO NOT add extra business rules
        // ✅ active defaults to true in entity

        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deactivateProduct(Long id) {
        Product product = getProductById(id);
        product.setActive(false);
        productRepository.save(product);
    }
}
    