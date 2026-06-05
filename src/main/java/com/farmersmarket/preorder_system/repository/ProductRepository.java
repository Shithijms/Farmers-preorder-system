package com.farmersmarket.preorder_system.repository;

import com.farmersmarket.preorder_system.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // By extending JpaRepository, Spring automatically gives you methods like:
    // save(), findAll(), findById(), deleteById() - no SQL required!
}