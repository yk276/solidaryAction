package com.example.solidaryaction.application.DTOs;

import com.example.solidaryaction.domain.entities.Usuario;
import com.example.solidaryaction.domain.valueobject.CPF;

public record UsuarioLogado(
        Long id,
        String email,
        CPF cpf,
        Long ong_id,
        java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> authorities
) {

    public UsuarioLogado(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getOng() != null ? usuario.getOng().getId() : null,
                usuario.getAuthorities()
        );
    }
}