package com.oxcart.project.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    private static final String SECURITY_SCHEME_NAME = "BearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // 1. CONFIGURAÇÃO DOS SERVIDORES (Resolve o erro de CORS/HTTPS no Railway)
                .servers(List.of(
                        new Server().url("https://backendoxcart-production.up.railway.app/oxcart").description("Servidor de Produção (Railway)"),
                        new Server().url("http://localhost:8080/oxcart").description("Servidor Local")
                ))
                // 2. INFORMAÇÕES DA API
                .info(new Info().title("Oxcart API").version("v1").description("API para gerenciamento de aeronaves e batalhas."))
                // 3. CONFIGURAÇÃO DE SEGURANÇA (JWT)
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ));
    }
}