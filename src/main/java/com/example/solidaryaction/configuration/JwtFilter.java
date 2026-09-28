package com.example.solidaryaction.configuration;

import com.example.solidaryaction.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Filtro executado uma vez por requisição. Decide quais rotas passam sem token e, nas demais, lê
//o cabeçalho Authorization e valida o JWT antes de continuar a cadeia de filtros.
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired // injeção de dependencia
    private TokenService tokenService;

    @Override
    //Este filtro intercepta requisições antes de chegarem aos controllers para verificar se existe um token JWT
    //válido.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        if(uri.startsWith("/swagger-ui")
        || uri.startsWith("/v2/api-docs")
        || uri.startsWith("/v3/api-docs")
        || uri.startsWith("/swagger-resources")
        || uri.startsWith("webjars")
        || uri.startsWith("/auth/login")
        || uri.startsWith("/cadastrar")
                || uri.startsWith("/")
        ){
            //Se a validação permitir, a requisição continua pela cadeia de filtros até chegar ao endpoint solicitado.
            filterChain.doFilter(request,response);
            return;
        }

        //Lê o cabeçalho Authorization, local onde normalmente chega o token no formato Bearer <token>.
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.replace("Bearer ", "");

            try{

                var jwtValidador = tokenService.verificarToken(token);

                System.out.println(jwtValidador.getSubject());

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("Token invalido");
                return;
            }
        }else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Token invalido");
            return;
        }


        filterChain.doFilter(request,response);

    }
}
