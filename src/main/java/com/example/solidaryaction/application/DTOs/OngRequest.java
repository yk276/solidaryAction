package com.example.solidaryaction.application.DTOs;

public record OngRequest(
        String nomeFantasia,
        String razaoSocial,
        String cnpj,
        String email,
        String endereco
) {
}