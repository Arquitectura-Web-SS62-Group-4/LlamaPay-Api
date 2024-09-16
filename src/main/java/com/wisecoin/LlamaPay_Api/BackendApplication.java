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
				categoryRepository.save(new Category(0L, "Servicios", "Gasto"));
				categoryRepository.save(new Category(0L, "Transporte", "Gasto"));
				categoryRepository.save(new Category(0L, "Alimentación", "Gasto"));
				categoryRepository.save(new Category(0L, "Salud y Cuidado Personal", "Gasto"));
				categoryRepository.save(new Category(0L, "Entretenimiento", "Gasto"));
				categoryRepository.save(new Category(0L, "Impuestos y Seguros", "Gasto"));
				categoryRepository.save(new Category(0L, "Otros Gastos", "Gasto"));

				// Categorías de tipo "Ingreso"
				categoryRepository.save(new Category(0L, "Salario y Bonificaciones", "Ingreso"));
				categoryRepository.save(new Category(0L, "Ventas de Artículos", "Ingreso"));
				categoryRepository.save(new Category(0L, "Dividendos e Inversiones", "Ingreso"));
				categoryRepository.save(new Category(0L, "Rentas e Ingresos de Propiedades", "Ingreso"));
				categoryRepository.save(new Category(0L, "Premios y Devoluciones", "Ingreso"));
				categoryRepository.save(new Category(0L, "Ahorros e Intereses Bancarios", "Ingreso"));
				categoryRepository.save(new Category(0L, "Otros Ingresos", "Ingreso"));
			}

		};
	}

}
