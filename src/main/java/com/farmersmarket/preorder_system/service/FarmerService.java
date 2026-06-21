package com.farmersmarket.preorder_system.service;

import com.farmersmarket.preorder_system.dto.FarmerRequest;
import com.farmersmarket.preorder_system.model.Farmer;
import com.farmersmarket.preorder_system.repository.FarmerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmerService {

    private final FarmerRepository farmerRepository;

    public FarmerService(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    public List<Farmer> getAllFarmers() {
        return farmerRepository.findAll();
    }

    public Farmer addFarmer(FarmerRequest request) {
        Farmer farmer = new Farmer();
        farmer.setName(request.getName());
        farmer.setFarmName(request.getFarmName());
        farmer.setContactEmail(request.getContactEmail());
        return farmerRepository.save(farmer);
    }
}
