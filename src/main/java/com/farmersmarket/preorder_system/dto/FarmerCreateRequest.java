package com.farmersmarket.preorder_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FarmerCreateRequest {
    @NotBlank(message = "Farmer name is required")
    private String name;

    @NotBlank(message = "Farm name is required")
    private String farmName;

    @NotBlank(message = "Contact email is required")
    @Email(message = "Contact email must be valid")
    private String contactEmail;
}
