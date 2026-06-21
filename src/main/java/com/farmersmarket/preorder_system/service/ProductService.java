package com.farmersmarket.preorder_system.service;

import com.farmersmarket.preorder_system.dto.ProductRequest;
import com.farmersmarket.preorder_system.exception.ResourceNotFoundException;
import com.farmersmarket.preorder_system.model.Farmer;
import com.farmersmarket.preorder_system.model.Product;
import com.farmersmarket.preorder_system.repository.FarmerRepository;
import com.farmersmarket.preorder_system.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final FarmerRepository farmerRepository;

    public ProductService(ProductRepository productRepository,
                          FarmerRepository farmerRepository) {
        this.productRepository = productRepository;
        this.farmerRepository = farmerRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(ProductRequest request) {
        Farmer farmer = farmerRepository.findById(request.getFarmerId())
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with ID: " + request.getFarmerId()));

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setAvailableQuantity(request.getAvailableQuantity());
        product.setFarmer(farmer);
        return productRepository.save(product);
    }
}

