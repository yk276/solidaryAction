package com.example.solidaryaction.controllers;

import com.example.solidaryaction.DTOs.LoginRequest;
import com.example.solidaryaction.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;

@RestController
@RequestMapping("/auth")
@Tag(description = "Controller de autenticação!", name = "Autenticação")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Operation(description = "Metodo de login", summary = "Autenticação de usuários")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        if(loginRequest.email().equals("string")&& loginRequest.senha().equals("string")){
            //gerar o token

            var token = tokenService.gerarToken(loginRequest.email());

            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();
    }
}
