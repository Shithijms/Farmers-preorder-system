package com.farmersmarket.preorder_system.controller;

import com.farmersmarket.preorder_system.model.Product;
import com.farmersmarket.preorder_system.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
@RestController // Indicates this class handles REST HTTP requests
@CrossOrigin(origins = "*")
@RequestMapping("/api/products") // The base URL for all endpoints in this class
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Handles GET requests to /api/products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Handles POST requests to /api/products
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        // @RequestBody converts the incoming JSON into a Java Product object
        return productService.addProduct(product);
    }
}
