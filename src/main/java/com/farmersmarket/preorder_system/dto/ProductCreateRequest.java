package com.farmersmarket.preorder_system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCreateRequest {
    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be zero or greater")
    private Double price;

    @NotNull(message = "Available quantity is required")
    @Min(value = 0, message = "Quantity must be zero or greater")
    private Integer availableQuantity;

    @NotNull(message = "Farmer ID is required")
    private Long farmerId;
}
