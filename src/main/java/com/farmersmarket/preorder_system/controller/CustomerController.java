package com.farmersmarket.preorder_system.controller;

import com.farmersmarket.preorder_system.dto.CustomerRequest;
import com.farmersmarket.preorder_system.model.Customer;
import com.farmersmarket.preorder_system.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping
    public Customer createCustomer(@Valid @RequestBody CustomerRequest request) {
        return customerService.addCustomer(request);
    }
}
