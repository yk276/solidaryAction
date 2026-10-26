package com.example.solidaryaction.application.DTOs;

import com.example.solidaryaction.domain.entities.Usuario;

public record UsuarioResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        EnumStatusUsuario status,
        String role,
        Long ongId
) {

    public UsuarioResponse(Usuario usuarioEntidade) {
        this(
                usuarioEntidade.getId(),
                usuarioEntidade.getNome(),
                usuarioEntidade.getCpf().toString(),
                usuarioEntidade.getEmail(),
                usuarioEntidade.getStatus(),
                usuarioEntidade.getRole(),
                usuarioEntidade.getOng() != null
                        ? usuarioEntidade.getOng().getId()
                        : null
        );
    }
}