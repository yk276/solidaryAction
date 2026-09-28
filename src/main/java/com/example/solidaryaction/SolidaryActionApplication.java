package com.example.solidaryaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SolidaryActionApplication {
    //Classe de inicialização do Spring Boot. O método main sobe o contexto da aplicação e inicia
    //o servidor embutido com toda a configuração do projeto.
    public static void main(String[] args) {
        SpringApplication.run(SolidaryActionApplication.class, args);
    }

}
