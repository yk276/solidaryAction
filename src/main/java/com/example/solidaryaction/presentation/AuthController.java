package com.example.solidaryaction.presentation;

//Controller de autenticação. Expõe o endpoint de login, consulta o repositório de usuários para
//validar credenciais e solicita ao TokenService a geração do JWT.
import com.example.solidaryaction.application.DTOs.LoginRequest;
import com.example.solidaryaction.application.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;

//Este controller recebe requisições HTTP desta entidade. @RequestMapping define a rota base e o Swagger usa
//@Tag/@Operation para documentar os endpoints.
@RestController
@RequestMapping("/auth")
@Tag(description = "Controller responsavel pela autenticação!", name = "Autenticação controller")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;


    //Endpoint POST: recebe um objeto no corpo da requisição e salva um novo registro usando o Repository.
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Metodo responsavel por efetuar o login do usuario")
    //O login recebe as credenciais, valida o usuário e, quando a autenticação é aceita, gera a resposta usada pelo
    //frontend.
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        var resultadoAutenticacaoRetornoToken = usuarioService.validarUsuarioAutenticadoRetornaToken(loginRequest);
        if(resultadoAutenticacaoRetornoToken != null){
            return ResponseEntity.ok(resultadoAutenticacaoRetornoToken);

        }
        return ResponseEntity.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();
    }
}
