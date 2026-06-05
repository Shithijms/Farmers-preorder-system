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

    @Transactional
    public PreOrder createPreOrder(PreOrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + request.getCustomerId()));

        List<Product> products = productRepository.findAllById(request.getProductIds());
        if (products.isEmpty()) {
            throw new RuntimeException("No valid products selected for pre-order.");
        }

        // --- NEW BUSINESS LOGIC: Inventory Management ---
        for (Product product : products) {
            if (product.getAvailableQuantity() <= 0) {
                throw new RuntimeException("Product '" + product.getName() + "' is out of stock.");
            }
            // Decrement the quantity.
            // Hibernate's 'Dirty Checking' will auto-save this to the database!
            product.setAvailableQuantity(product.getAvailableQuantity() - 1);
        }
        // ------------------------------------------------

        PreOrder preOrder = new PreOrder();
        preOrder.setCustomer(customer);
        preOrder.setProducts(products);
        preOrder.setOrderDate(LocalDateTime.now());
        preOrder.setStatus("PENDING");

        return preOrderRepository.save(preOrder);
    }
    public PreOrder getOrderById(Long id) {
        return preOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    public List<PreOrder> getAllOrders() {
        return preOrderRepository.findAll();
    }
}