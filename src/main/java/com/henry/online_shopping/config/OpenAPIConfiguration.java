package com.henry.online_shopping.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.henry.online_shopping.constant.OpenAPIConstant.*;

/**
 * @apiNote To configure this class, of course you have to install Swagger first by adding this line into your Gradle build file, then re-sync project: <br>
 * (I assume you just want exploring my code instead of cloning it :v)
 * <pre><code>
 * dependencies {
 *     ... // Other dependencies
 *
 *     // Gradle Groovy DSL (build.gradle)
 *     implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0'
 *
 *     // or Gradle Kotlin DSL (build.gradle.kts)
 *     implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
 * }
 * </code></pre>
 * or Maven build file ({@code pom.xml}):
 * <pre><code>
 * <dependency>
 *     <groupId>org.springdoc</groupId>
 *     <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
 *     <version>2.7.0</version>
 * </dependency>
 * </code></pre>
 */
@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
                .openapi(OPENAPI_VERSION)
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
