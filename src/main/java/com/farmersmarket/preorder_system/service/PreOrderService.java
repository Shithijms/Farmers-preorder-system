package com.farmersmarket.preorder_system.service;

import com.farmersmarket.preorder_system.dto.PreOrderRequest;
import com.farmersmarket.preorder_system.model.Customer;
import com.farmersmarket.preorder_system.model.PreOrder;
import com.farmersmarket.preorder_system.model.Product;
import com.farmersmarket.preorder_system.repository.CustomerRepository;
import com.farmersmarket.preorder_system.repository.PreOrderRepository;
import com.farmersmarket.preorder_system.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PreOrderService {

    private final PreOrderRepository preOrderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    // Spring injects all three required repositories automatically via constructor
    public PreOrderService(PreOrderRepository preOrderRepository,
                           CustomerRepository customerRepository,
                           ProductRepository productRepository) {
        this.preOrderRepository = preOrderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional // Ensures database integrity. All operations succeed, or all roll back.
    public PreOrder createPreOrder(PreOrderRequest request) {
        // 1. Fetch the customer from database
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + request.getCustomerId()));

        // 2. Fetch all requested products by their IDs
        List<Product> products = productRepository.findAllById(request.getProductIds());
        if (products.isEmpty()) {
            throw new RuntimeException("No valid products selected for pre-order.");
        }

        // 3. Assemble the PreOrder entity
        PreOrder preOrder = new PreOrder();
        preOrder.setCustomer(customer);
        preOrder.setProducts(products);
        preOrder.setOrderDate(LocalDateTime.now());
        preOrder.setStatus("PENDING");

        // 4. Save to the database
        return preOrderRepository.save(preOrder);
    }

    public List<PreOrder> getAllOrders() {
        return preOrderRepository.findAll();
    }
}