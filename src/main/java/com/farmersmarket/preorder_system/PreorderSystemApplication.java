package com.farmersmarket.preorder_system;
import com.farmersmarket.preorder_system.model.Farmer;
import com.farmersmarket.preorder_system.repository.FarmerRepository;
import com.farmersmarket.preorder_system.model.Customer;
import com.farmersmarket.preorder_system.model.Farmer;
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
	public CommandLineRunner dataInitializer(CustomerRepository customerRepo,
	                                         ProductRepository productRepo,
	                                         FarmerRepository farmerRepo) { // Inject Farmer Repo
		return args -> {
			// Seed a Farmer
			Farmer farmer = new Farmer();
			farmer.setName("Old MacDonald");
			farmer.setFarmName("MacDonald Family Farms");
			farmer.setContactEmail("contact@macdonald.com");
			farmerRepo.save(farmer);

			// Seed a test Customer
			Customer customer = new Customer();
			customer.setName("Test User");
			customer.setEmail("test@example.com");
			customer.setPhoneNumber("9876543210");
			customerRepo.save(customer);

			// Seed a test Product AND link it to the Farmer
			Product product = new Product();
			product.setName("Fresh Strawberries");
			product.setDescription("Organic farm fresh strawberries");
			product.setPrice(5.99);
			product.setAvailableQuantity(25);
			product.setFarmer(farmer); // Wire the relationship!
			productRepo.save(product);
		};
	}
}