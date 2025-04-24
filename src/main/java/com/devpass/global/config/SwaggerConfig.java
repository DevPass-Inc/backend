package com.devpass.global.config;

import io.swagger.v3.oas.models.Components;

import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.devpass.global.annotation.AuthUser;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .components(new Components())
            .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
            .title("Devpass API")
            .description("Devpass API Docs")
            .version("1.0");
    }
}
