package com.farmersmarket.preorder_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FarmerRequest {

    @NotBlank(message = "Farmer name is required")
    private String name;

    @NotBlank(message = "Farm name is required")
    private String farmName;

    @Email(message = "Enter a valid email address")
    @NotBlank(message = "Contact email is required")
    private String contactEmail;
}
