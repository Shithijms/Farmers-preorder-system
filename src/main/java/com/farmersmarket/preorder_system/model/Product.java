package com.farmersmarket.preorder_system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Integer availableQuantity;

    // Many Products belong to one Farmer
    @ManyToOne
    @JoinColumn(name = "farmer_id") // Creates a foreign key column in the database
    private Farmer farmer;
}