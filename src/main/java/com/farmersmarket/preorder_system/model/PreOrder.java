package com.farmersmarket.preorder_system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class PreOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;
    private String status; // e.g., "PENDING", "CONFIRMED", "COMPLETED"

    // Many orders belong to one Customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // A single order can have multiple products, and a product can be in multiple orders
    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;
}