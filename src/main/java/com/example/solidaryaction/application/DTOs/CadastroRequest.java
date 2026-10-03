package com.example.solidaryaction.application.DTOs;

//Transporta apenas os dados necessários para uma requisição/resposta
//específica, evitando usar uma entidade completa quando não é preciso.
public record CadastroRequest(String nome, String cpf, String email) {
}
