package com.farmersmarket.preorder_system;

import com.farmersmarket.preorder_system.model.Customer;
import com.farmersmarket.preorder_system.model.Product;
import com.farmersmarket.preorder_system.repository.CustomerRepository;
import com.farmersmarket.preorder_system.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PreorderSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreorderSystemApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataInitializer(CustomerRepository customerRepo, ProductRepository productRepo) {
		return args -> {
			// Seed a test Customer
			Customer customer = new Customer();
			customer.setName("Shithij");
			customer.setEmail("shithij@example.com");
			customer.setPhoneNumber("9876543210");
			customerRepo.save(customer);

			// Seed a test Product
			Product product = new Product();
			product.setName("Fresh Strawberries");
			product.setDescription("Organic farm fresh strawberries");
			product.setPrice(5.99);
			product.setAvailableQuantity(25);
			productRepo.save(product);
		};
	}
}