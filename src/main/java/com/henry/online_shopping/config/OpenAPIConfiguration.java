package com.henry.online_shopping.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.henry.online_shopping.constant.OpenAPIConstant.*;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
                .openapi(VERSION)
                .info(new Info().title(TITLE).version(API_VERSION))
                .components(new Components().addSecuritySchemes(
                        "Bearer Token",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer")
                ));
    }
}
