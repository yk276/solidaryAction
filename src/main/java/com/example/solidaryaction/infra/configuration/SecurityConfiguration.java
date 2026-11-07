package com.example.solidaryaction.infra.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/login",
                                "/swagger-ui/**",
                                "/webjars?**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        //O cadastro do desktop valida a chave no service.
                        .requestMatchers(
                                HttpMethod.POST,
                                "/ongs/cadastro-com-coordenador"
                        )
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/campanhas",
                                "/campanhas/**"
                        )
                        .hasAnyRole("ADMIN", "COORDENADOR", "USER")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/campanhas",
                                "/campanhas/**"
                        )
                        .hasRole("COORDENADOR")

                        .requestMatchers("/campanhas", "/campanhas/**")
                        .hasRole("COORDENADOR")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/doadores",
                                "/doadores/**"
                        )
                        .hasAnyRole("ADMIN", "COORDENADOR", "USER")

                        .requestMatchers("/doadores", "/doadores/**")
                        .hasRole("COORDENADOR")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/doacoes",
                                "/doacoes/**"
                        )
                        .hasAnyRole("ADMIN", "COORDENADOR", "USER")

                        .requestMatchers("/doacoes", "/doacoes/**")
                        .hasRole("COORDENADOR")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/usuarios/admin"
                        )
                        .permitAll()

                        //Todos os perfis podem consultar a própria conta.
                        //Esta regra precisa vir antes de /usuarios/**.
                        .requestMatchers(
                                HttpMethod.GET,
                                "/usuarios/buscarusuariologado"
                        )
                        .hasAnyRole("ADMIN", "COORDENADOR", "USER")

                        //A gestão de usuários fica restrita a estes perfis.
                        .requestMatchers("/usuarios", "/usuarios/**")
                        .hasAnyRole("ADMIN", "COORDENADOR")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/ongs/*/coordenadores"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/ongs",
                                "/ongs/**"
                        )
                        .hasAnyRole("ADMIN", "COORDENADOR", "USER")

                        .requestMatchers(HttpMethod.PUT, "/ongs/*")
                        .hasAnyRole("ADMIN", "COORDENADOR")

                        .requestMatchers("/ongs", "/ongs/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}