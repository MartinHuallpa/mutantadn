package com.utn.adn.mutantadn.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mutant Detector API")
                        .version("1.0")
                        .description("""
                                Plataforma de análisis genético para la identificación de mutantes.
                                Esta API procesa secuencias de ADN representadas en matrices NxN.
                                Busca coincidencias de bases nitrogenadas (A, T, C, G).
                                """)
                        .contact(new Contact()
                                .name("Martin Huallpa")
                                .email("martinhualpa1@gmail.com")));
    }
}