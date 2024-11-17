package com.wisecoin.LlamaPay_Api;

import com.wisecoin.LlamaPay_Api.entities.Authority;
import com.wisecoin.LlamaPay_Api.entities.Bit;
import com.wisecoin.LlamaPay_Api.entities.Category;
import com.wisecoin.LlamaPay_Api.entities.Premiun;
import com.wisecoin.LlamaPay_Api.repositories.*;
import com.wisecoin.LlamaPay_Api.services.AuthorityService;
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
			BitRepository bitRepository,
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

			if (bitRepository.count() == 0) {
				bitRepository.save(new Bit(0L, "Tipos de Interés", "El interés es el costo de pedir dinero prestado o el rendimiento de una inversión. Existen diferentes tipos de interés, como el simple, compuesto y el variable. Cada tipo tiene sus características y aplicaciones, afectando tanto los préstamos como los ahorros de manera distinta.",
						"https://www.masterlogistica.es/wp-content/uploads/2020/05/tasa-de-interes.jpg"));
				bitRepository.save(new Bit(0L, "Pólizas de Seguro", "Las pólizas de seguro son contratos que brindan protección financiera ante riesgos específicos, como accidentes, enfermedades o daños. Dependiendo del tipo de seguro, pueden cubrir desde daños a propiedades hasta gastos médicos, asegurando que los asegurados reciban apoyo económico en momentos difíciles.",
						"https://elperuano.pe/fotografia/thumbnail/2023/07/25/000258179M.jpg"));
				bitRepository.save(new Bit(0L, "Tipos de Inversión", "Existen varias formas de invertir, como en bienes raíces, acciones, bonos y fondos mutuos. Cada tipo tiene sus propios riesgos y rendimientos. Las inversiones en acciones ofrecen alto potencial de crecimiento, mientras que los bonos suelen ser más seguros pero con rendimientos menores.",
						"https://media.istockphoto.com/id/1399211832/es/vector/asignaci%C3%B3n-de-activos-inversi%C3%B3n.jpg?s=612x612&w=0&k=20&c=P8d7t4Sf3ymIQKuBJSJ-Iq46G12PurdoIXWCMaxQiJM="));
				bitRepository.save(new Bit(0L, "Formas de Ahorrar", "El ahorro es esencial para asegurar un futuro financiero estable. Se puede ahorrar a través de cuentas bancarias, fondos de inversión, o incluso en sistemas de ahorro como los planes de jubilación. La clave es establecer metas claras y ser constante en los depósitos para generar interés.",
						"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWreSeZsyPFgYjKwYkueSSnbG4mN-7mkUCKg&s"));
				bitRepository.save(new Bit(0L, "Tipos de Crédito", "El crédito es un acuerdo en el que una persona recibe dinero prestado y se compromete a devolverlo con intereses. Los tipos de crédito incluyen el crédito personal, tarjetas de crédito y líneas de crédito. Cada tipo tiene condiciones diferentes, como los límites y tasas de interés, que varían según el prestamista.",
						"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_Uys3pTLC3_SWDn_bLCQxPp32hkQiGZBOMQ&s"));
				bitRepository.save(new Bit(0L, "Depósito a Plazo Fijo", "Un depósito a plazo fijo es una inversión en la que se deposita una cantidad de dinero en el banco por un período determinado, a cambio de un interés fijo. Es una opción segura, ya que el capital está garantizado, aunque generalmente los rendimientos son más bajos en comparación con inversiones más riesgosas.",
						"https://img.freepik.com/vector-gratis/ilustracion-dia-pago-dibujada-mano_23-2151084539.jpg"));
			}


			if(premiunRepository.count() == 0){
				premiunRepository.save(new Premiun(0L,15.0));
			}
		};
	}

}
