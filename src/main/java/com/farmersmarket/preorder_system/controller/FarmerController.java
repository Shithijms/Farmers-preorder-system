package com.farmersmarket.preorder_system.controller;

import com.farmersmarket.preorder_system.dto.FarmerRequest;
import com.farmersmarket.preorder_system.model.Farmer;
import com.farmersmarket.preorder_system.service.FarmerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/farmers")
public class FarmerController {

    private final FarmerService farmerService;

    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    @GetMapping
    public List<Farmer> getAllFarmers() {
        return farmerService.getAllFarmers();
    }

    @PostMapping
    public Farmer createFarmer(@Valid @RequestBody FarmerRequest request) {
        return farmerService.addFarmer(request);
    }
}
