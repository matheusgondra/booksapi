package com.matheusgondra.booksapi.infrastructure.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .servers(Collections.singletonList(new Server().url("/api")))
                .info(new Info().title("Books API")
                        .description("API documentation for the Books API")
                        .version("1.0.0"));
    }
}
