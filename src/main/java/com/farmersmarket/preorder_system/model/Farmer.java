package com.farmersmarket.preorder_system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @author Shithij
 */

@Entity // Tells JPA to make a table out of this class
@Data // Lombok: auto-generates getters, setters, toString, etc.
@NoArgsConstructor // Lombok: auto-generates an empty constructor required by JPA
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String farmName;
    private String contactEmail;

    // A Farmer can have many Products
    @OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL)
    private List<Product> products;
}