package com.farmersmarket.preorder_system.controller;

import com.farmersmarket.preorder_system.dto.ProductRequest;
import com.farmersmarket.preorder_system.model.Product;
import com.farmersmarket.preorder_system.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@Valid @RequestBody ProductRequest request) {
        return productService.addProduct(request);
    }
}
