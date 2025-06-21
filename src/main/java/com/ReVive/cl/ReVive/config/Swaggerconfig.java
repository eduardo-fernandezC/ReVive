package com.ReVive.cl.ReVive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Swaggerconfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI() 
            .info( new Info()
                    .title("API REVIVE")
                    .version("1.3")
                    .description("Documentación completa de la API RESTful de REVIVE")
                    .contact(new Contact()
                    .name("Equipo REVIVE")
                    .email("soporte@revive.cl")));
    }
}
