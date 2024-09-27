package com.wisecoin.LlamaPay_Api;

import com.wisecoin.LlamaPay_Api.entities.Authority;
import com.wisecoin.LlamaPay_Api.entities.Category;
import com.wisecoin.LlamaPay_Api.entities.Premiun;
import com.wisecoin.LlamaPay_Api.entities.TypeBit;
import com.wisecoin.LlamaPay_Api.repositories.*;
import com.wisecoin.LlamaPay_Api.services.AuthorityService;
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
			CategoryRepository categoryRepository,
			TypeBitRepository typeBitRepository,
			PremiunRepository premiunRepository,
			AuthorityRepository authorityRepository,
			AuthorityService authorityService

	) {
		return args -> {
			if (authorityRepository.count() == 0) {
				authorityService.addAuthority(new Authority(0L,"ADMIN",null));
				authorityService.addAuthority(new Authority(0L,"CLIENTE",null));
			}


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

			if(typeBitRepository.count() == 0){
				typeBitRepository.save(new TypeBit(0L, "Interés"));
				typeBitRepository.save(new TypeBit(0L, "Póliza de Seguro"));
				typeBitRepository.save(new TypeBit(0L, "Inversión"));
				typeBitRepository.save(new TypeBit(0L, "Ahorro"));
				typeBitRepository.save(new TypeBit(0L, "Crédito"));
			}

			if(premiunRepository.count() == 0){
				premiunRepository.save(new Premiun(0L,15.0));
			}
		};
	}

}
