package com.wisecoin.LlamaPay_Api;

import com.wisecoin.LlamaPay_Api.entities.Category;
import com.wisecoin.LlamaPay_Api.repositories.CategoryRepository;
import com.wisecoin.LlamaPay_Api.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner mappingDemo(
			CategoryRepository categoryRepository
	) {
		return args -> {
			if (categoryRepository.count() == 0) {

				// Categorías de tipo "Gasto"
				categoryRepository.save(new Category(0L, "Vivienda", "Gasto"));
				categoryRepository.save(new Category(0L, "Servicios Públicos", "Gasto"));
				categoryRepository.save(new Category(0L, "Ropa", "Gasto"));
				categoryRepository.save(new Category(0L, "Internet", "Gasto"));
				categoryRepository.save(new Category(0L, "Teléfono", "Gasto"));
				categoryRepository.save(new Category(0L, "Mascotas", "Gasto"));
				categoryRepository.save(new Category(0L, "Aseo Personal", "Gasto"));
				categoryRepository.save(new Category(0L, "Seguros", "Gasto"));
				categoryRepository.save(new Category(0L, "Hogar", "Gasto"));
				categoryRepository.save(new Category(0L, "Impuestos", "Gasto"));

				// Categorías de tipo "Ingreso"
				categoryRepository.save(new Category(0L, "Freelance", "Ingreso"));
				categoryRepository.save(new Category(0L, "Bonificación", "Ingreso"));
				categoryRepository.save(new Category(0L, "Dividendos", "Ingreso"));
				categoryRepository.save(new Category(0L, "Premios", "Ingreso"));
				categoryRepository.save(new Category(0L, "Devolución", "Ingreso"));
				categoryRepository.save(new Category(0L, "Rentas", "Ingreso"));
				categoryRepository.save(new Category(0L, "Ahorros", "Ingreso"));
				categoryRepository.save(new Category(0L, "Intereses Bancarios", "Ingreso"));
				categoryRepository.save(new Category(0L, "Venta de Activos", "Ingreso"));
				categoryRepository.save(new Category(0L, "Consultoría", "Ingreso"));
			}

		};
	}

}
