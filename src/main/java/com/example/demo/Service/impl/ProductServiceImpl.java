package com.example.demo.serviceimpl;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService
{
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product)
    {
        return productRepository.save(product);
    }

    public Product getProductById(Long id)
    {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }
}
