package com.farmersmarket.preorder_system.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class PreOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Double totalAmount;

    // Many orders belong to one Customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // A single pre-order can contain multiple order items
    @OneToMany(mappedBy = "preOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("preOrder")
    private List<OrderProduct> orderProducts = new ArrayList<>();
}