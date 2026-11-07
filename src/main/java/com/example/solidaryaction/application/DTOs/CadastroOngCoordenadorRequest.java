package com.example.solidaryaction.application.DTOs;

public record CadastroOngCoordenadorRequest(
        String nomeFantasia,
        String razaoSocial,
        String cnpj,
        String email,
        String endereco,
        String nomeCoordenador,
        String cpfCoordenador,
        String emailCoordenador,
        String senhaCoordenador,
        String secretKey
) {
}