package com.example.solidaryaction.application.DTOs;

public record CriarOngAdminRequest(
        String nomeFantasia,
        String razaoSocial,
        String cnpj,
        String email,
        String endereco,
        String secretKey
) {
}