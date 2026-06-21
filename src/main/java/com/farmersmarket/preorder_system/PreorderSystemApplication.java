package com.farmersmarket.preorder_system;

import com.farmersmarket.preorder_system.model.Customer;
import com.farmersmarket.preorder_system.model.Farmer;
import com.farmersmarket.preorder_system.model.Product;
import com.farmersmarket.preorder_system.repository.CustomerRepository;
import com.farmersmarket.preorder_system.repository.FarmerRepository;
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
                                             FarmerRepository farmerRepo) {
        return args -> {
            Farmer farmer1 = new Farmer();
            farmer1.setName("Old MacDonald");
            farmer1.setFarmName("MacDonald Family Farms");
            farmer1.setContactEmail("contact@macdonald.com");
            farmerRepo.save(farmer1);

            Farmer farmer2 = new Farmer();
            farmer2.setName("Luna Green");
            farmer2.setFarmName("Green Valley Produce");
            farmer2.setContactEmail("luna@greenvalley.com");
            farmerRepo.save(farmer2);

            Customer customer1 = new Customer();
            customer1.setName("Alice Parker");
            customer1.setEmail("alice.parker@example.com");
            customer1.setPhoneNumber("555-1234");
            customerRepo.save(customer1);

            Customer customer2 = new Customer();
            customer2.setName("Ben Johnson");
            customer2.setEmail("ben.johnson@example.com");
            customer2.setPhoneNumber("555-9876");
            customerRepo.save(customer2);

            Product strawberries = new Product();
            strawberries.setName("Fresh Strawberries");
            strawberries.setDescription("Organic farm fresh strawberries");
            strawberries.setPrice(5.99);
            strawberries.setAvailableQuantity(25);
            strawberries.setFarmer(farmer1);
            productRepo.save(strawberries);

            Product carrots = new Product();
            carrots.setName("Rainbow Carrots");
            carrots.setDescription("Crunchy carrots picked fresh this morning.");
            carrots.setPrice(3.49);
            carrots.setAvailableQuantity(30);
            carrots.setFarmer(farmer2);
            productRepo.save(carrots);

            Product basil = new Product();
            basil.setName("Aromatic Basil");
            basil.setDescription("Bright green basil leaves for pesto and salads.");
            basil.setPrice(2.99);
            basil.setAvailableQuantity(15);
            basil.setFarmer(farmer1);
            productRepo.save(basil);
        };
    }
}