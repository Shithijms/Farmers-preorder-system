package com.farmersmarket.preorder_system.repository;

import com.farmersmarket.preorder_system.model.PreOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreOrderRepository extends JpaRepository<PreOrder, Long> {
}