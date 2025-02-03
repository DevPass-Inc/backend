package com.devpass.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                ;
    }

    private Info apiInfo() {
        return new Info()
                .title("Devpass API")
                .description("Devpass API Docs")
                .version("1.0");
    }
}
