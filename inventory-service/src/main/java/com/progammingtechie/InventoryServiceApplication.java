package com.progammingtechie;

import com.progammingtechie.entity.Inventory;
import com.progammingtechie.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			if (inventoryRepository.findBySkuCode("iphone_13").isEmpty()) {
				Inventory inventory = new Inventory();
				inventory.setSkuCode("iphone_13");
				inventory.setQuantity(100);
				inventoryRepository.save(inventory);
			}

			if (inventoryRepository.findBySkuCode("iphone_13_red").isEmpty()) {
				Inventory inventory2 = new Inventory();
				inventory2.setSkuCode("iphone_13_red");
				inventory2.setQuantity(0);
				inventoryRepository.save(inventory2);
			}
		};
	}

}
