package com.farmersmarket.preorder_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // 1. Add this import
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String farmName;
    private String contactEmail;

    @OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL)
    @JsonIgnore // 2. Add this annotation to break the infinite loop!
    private List<Product> products;
}