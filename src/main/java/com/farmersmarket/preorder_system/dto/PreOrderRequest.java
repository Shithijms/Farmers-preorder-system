package com.farmersmarket.preorder_system.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PreOrderRequest {
    @NotNull(message = "Customer ID cannot be missing")
    private Long customerId;

    @NotEmpty(message = "At least one order item is required")
    @Valid
    private List<OrderProductRequest> orderProducts;
}