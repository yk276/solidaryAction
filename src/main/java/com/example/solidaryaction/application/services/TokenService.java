package com.example.solidaryaction.application.services;

//Serviço responsável por criar e validar tokens JWT.

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.solidaryaction.application.DTOs.EnumStatusUsuario;
import com.example.solidaryaction.application.DTOs.LoginRequest;
import com.example.solidaryaction.application.DTOs.UsuarioLogado;
import com.example.solidaryaction.domain.entities.Token;
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

    //Gera um JWT para um usuário ativo.
    public String gerarToken(LoginRequest loginResquestDto){

        Algorithm algorithm = Algorithm.HMAC256(secret);

        var usuario = usuarioRepository.findAll()
                .stream()
                .filter(u -> u.getEmail().equals(loginResquestDto.email()))
                .findFirst()
                .orElse(null);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }

        if (usuario.getStatus() != EnumStatusUsuario.ATIVO) {
            throw new IllegalArgumentException(
                    "Usuário bloqueado ou excluído!"
            );
        }

        String token = JWT.create()
                .withIssuer(emissor)
                .withSubject(loginResquestDto.email())
                .withExpiresAt(this.gerarDataExpiracao())
                .sign(algorithm);

        tokenRepository.save(new Token(token, usuario));

        return token;
    }

    //Valida o JWT e confere o status atual do usuário.
    public UsuarioLogado verificarToken(String token)
            throws JWTVerificationException {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTVerifier verificador = JWT.require(algorithm)
                .withIssuer(emissor)
                .build();

        verificador.verify(token);

        var tokenBanco = tokenRepository
                .findTokenByToken(token)
                .orElse(null);

        if (tokenBanco == null) {
            throw new IllegalArgumentException("Token inválido!");
        }

        var usuario = tokenBanco.getUsuario();

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }

        if (usuario.getStatus() != EnumStatusUsuario.ATIVO) {
            throw new IllegalArgumentException(
                    "Usuário bloqueado ou excluído!"
            );
        }

        return new UsuarioLogado(usuario);
    }

    private Instant gerarDataExpiracao(){

        //Pega a data atual.
        var dataAtual = LocalDateTime.now();

        //Adiciona o tempo de validade do token.
        var dataFutura = dataAtual.plusMinutes(expiracao);

        //Converte para um instante.
        return dataFutura.toInstant(ZoneOffset.of("-03:00"));
    }
}