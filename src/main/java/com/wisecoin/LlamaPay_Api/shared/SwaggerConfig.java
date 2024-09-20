package com.wisecoin.LlamaPay_Api.shared;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("LlamaPay API")
                        .description("LlamaPay API with Spring Security")
                        .version("1.0.0")
                );
    }
}
