package com.farmersmarket.preorder_system.controller;

import com.farmersmarket.preorder_system.dto.PreOrderRequest;
import com.farmersmarket.preorder_system.model.PreOrder;
import com.farmersmarket.preorder_system.service.PreOrderService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/orders")
public class PreOrderController {

    private final PreOrderService preOrderService;

    public PreOrderController(PreOrderService preOrderService) {
        this.preOrderService = preOrderService;
    }

    @PostMapping
    public PreOrder placeOrder(@RequestBody PreOrderRequest request) {
        return preOrderService.createPreOrder(request);
    }
    @GetMapping("/{id}")
    public PreOrder getOrderById(@PathVariable Long id) {
        return preOrderService.getOrderById(id);
    }
    @GetMapping
    public List<PreOrder> getAllOrders() {
        return preOrderService.getAllOrders();
    }
}