package com.example.solidaryaction.infra.external.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    //Autoriza o frontend em `localhost:3000` a chamar a API Spring Boot e
    //define quais métodos HTTP podem atravessar essa política.

    //Esta configuração define as regras de CORS para permitir que o frontend em outra origem/porta acesse a API.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //Aqui são definidas as rotas e origens permitidas nas requisições vindas do navegador.
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS","PATCH","HEAD");
    }
}
