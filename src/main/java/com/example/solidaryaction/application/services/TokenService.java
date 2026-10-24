package com.example.solidaryaction.application.services;

//Serviço responsável por criar e validar tokens JWT. Centraliza algoritmo, emissor, segredo e
//tempo de expiração configurados no `application.properties

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.solidaryaction.application.DTOs.LoginRequest;
import com.example.solidaryaction.application.DTOs.UsuarioLogado;
import com.example.solidaryaction.domain.entities.Token;
import com.example.solidaryaction.domain.entities.Usuario;
import com.example.solidaryaction.domain.repository.TokenRepository;
import com.example.solidaryaction.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.secret}")
    private String secret;

    @Value("${spring.expiracao}")
    private long expiracao;

    @Value("${spring.emissor}")
    private String emissor;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRepository tokenRepository;

    //Gera um JWT contendo as informações definidas pelo sistema, assina o token com a chave secreta e define sua
    //validade.
    public String gerarToken(LoginRequest loginResquestDto){

        Algorithm algorithm = Algorithm.HMAC256(secret);

        var usuario = usuarioRepository.findAll()
                .stream().filter( u-> u.getEmail().equals(loginResquestDto.email()))
                .findFirst().orElse(null);

        String token = JWT.create()
                .withIssuer(emissor)
                .withSubject(loginResquestDto.email())
                .withExpiresAt(this.gerarDataExpiracao())
                .sign(algorithm);


        tokenRepository.save(new Token(token,usuario));

        return token;
    }

    public UsuarioLogado verificarToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTVerifier verificador = JWT.require(algorithm).withIssuer(emissor).build();

        verificador.verify(token);

        var tokenBanco = tokenRepository.findTokenByToken(token).orElse(null);

        if(tokenBanco==null){
            throw new IllegalArgumentException("Token inválido!");
        }

        return new UsuarioLogado(tokenBanco.getUsuario());
    }

    private Instant gerarDataExpiracao(){

        //pegar data atual
        var dataAtual = LocalDateTime.now();
        //adicionar ou diminuir tempo da data atual
        var dataFutura = dataAtual.plusMinutes(expiracao);
        //convertendo instantes
        return dataFutura.toInstant(ZoneOffset.of("-03:00"));
    }
}
