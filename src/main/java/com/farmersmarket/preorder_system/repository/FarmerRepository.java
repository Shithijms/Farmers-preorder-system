package com.farmersmarket.preorder_system.repository; // Ensure this matches your package structure

import com.farmersmarket.preorder_system.model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
}