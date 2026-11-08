package com.example.solidaryaction.application.DTOs;

public record CnpjResponse(
        String cnpj,
        String razaoSocial,
        String nomeFantasia
) {
}