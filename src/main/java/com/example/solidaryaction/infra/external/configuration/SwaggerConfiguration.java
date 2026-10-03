package com.example.solidaryaction.infra.external.configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Configuração do OpenAPI/Swagger. Personaliza título e descrição da API e adiciona o esquema
//Bearer JWT ao botão Authorize do Swagger UI.
@Configuration
//Centraliza a configuração do OpenAPI/Swagger, usado para documentar e testar a API pela interface Swagger UI.
public class SwaggerConfiguration {


    @Bean
    public OpenAPI customOpemAPI(){

        //O objeto OpenAPI define informações exibidas na documentação, como título e descrição da API.
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                .info(new Info()
                        .title("Solidary Action")
                        .version("1.0.0")
                        .description("Api para projeto Solidary Action")
                );

    }
}
