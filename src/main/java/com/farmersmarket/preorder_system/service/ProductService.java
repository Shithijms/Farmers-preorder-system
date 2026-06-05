package com.farmersmarket.preorder_system.service;

import com.farmersmarket.preorder_system.model.Product;
import com.farmersmarket.preorder_system.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Tells Spring this is a business logic component
public class ProductService {

    private final ProductRepository productRepository;

    // Dependency Injection: Spring automatically provides the repository instance here
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        // Here we could add logic like: if (product.getPrice() < 0) throw Exception
        return productRepository.save(product);
    }
}

