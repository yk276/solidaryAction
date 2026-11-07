package com.example.solidaryaction.infra.configuration;

import com.example.solidaryaction.application.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Valida o JWT nas rotas que exigem autenticação por token.
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (uri.startsWith("/swagger-ui")
                || uri.startsWith("/v2/api-docs")
                || uri.startsWith("/v3/api-docs")
                || uri.startsWith("/swagger-resources")
                || uri.startsWith("webjars")
                || uri.startsWith("/auth/login")
                || uri.startsWith("/cadastrar")
                || (request.getMethod().equals("POST")
                && uri.equals("/usuarios/admin"))
                || (request.getMethod().equals("POST")
                && uri.equals("/ongs/cadastro-com-coordenador"))) {

            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.replace("Bearer ", "");

            try {
                var usuarioLogado = tokenService.verificarToken(token);

                var user = new UsernamePasswordAuthenticationToken(
                        usuarioLogado,
                        null,
                        usuarioLogado.authorities()
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(user);

            } catch (Exception e) {
                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );
                response.getWriter().println("Token invalido");
                return;
            }

        } else {
            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );
            response.getWriter().println("Token invalido");
            return;
        }

        filterChain.doFilter(request, response);
    }
}