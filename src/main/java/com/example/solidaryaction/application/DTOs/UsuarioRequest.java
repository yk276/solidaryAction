package com.example.solidaryaction.application.DTOs;

public record UsuarioRequest(
        String nome,
        String email,
        String senha,
        String cpf,
        String perfil,
        Long ongId
) {
}