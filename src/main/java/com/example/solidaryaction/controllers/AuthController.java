package com.example.solidaryaction.controllers;

import com.example.solidaryaction.DTOs.CadastroRequest;
import com.example.solidaryaction.DTOs.LoginRequest;
import com.example.solidaryaction.DTOs.LoginResponse;
import com.example.solidaryaction.repository.UsuarioRepository;
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
@Tag(description = "Controller responsavel pela autenticação!", name = "Autenticação controller")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Metodo responsavel por efetuar o login do usuario")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){



        if(usuarioRepository.existsUsuarioByEmailAndSenha(loginRequest.email(), loginRequest.senha())){
            //gerar o token

            var token = tokenService.gerarToken(loginRequest.email());

            return ResponseEntity.ok(new LoginResponse(token));
        }
        return ResponseEntity.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();
    }
}
