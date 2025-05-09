package com.samsungnomads.wheretogo.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {

    private static final String ACCESS_TOKEN_KEY = "Access Token (Bearer)";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(securityRequirement())
                .components(securityComponents());
    }

    private Info apiInfo() {
        return new Info()
                .title("wheretogo API");
    }

    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement()
                .addList(ACCESS_TOKEN_KEY);
    }

    private Components securityComponents() {
        return new Components()
                .addSecuritySchemes(ACCESS_TOKEN_KEY, accessTokenScheme());
    }

    private SecurityScheme accessTokenScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);
    }
}