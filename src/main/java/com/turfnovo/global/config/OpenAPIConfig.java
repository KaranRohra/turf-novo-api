package com.turfnovo.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {
  @Bean
  OpenAPI inventoryOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("Turf Novo API").version("1.0").description("Documentation Turf Novo API v1.0"));
  }
}