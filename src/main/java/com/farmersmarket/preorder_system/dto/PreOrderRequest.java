package com.farmersmarket.preorder_system.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class PreOrderRequest {
    @NotNull(message = "Customer ID cannot be missing")
    private Long customerId;
    @NotEmpty(message = "Should select at least one")
    private List<Long> productIds;
}