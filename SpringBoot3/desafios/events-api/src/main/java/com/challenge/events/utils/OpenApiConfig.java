package com.challenge.events.utils;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI OpenAPIConfiguration() {
        return new OpenAPI()
                .info(new Info()
                        .title("Events API")
                        .version("1.0")
                        .description("Documentação da API de Eventos.")
                        .contact(new Contact()
                                .name("Emmanuella Faustino Albuquerque")
                                .url("https://manu-portfolio-two.vercel.app")
                        )
                );
    }
}