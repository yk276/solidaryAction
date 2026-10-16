package com.example.solidaryaction.application.DTOs;

// Este DTO transporta apenas os dados necessários para uma requisição/resposta específica, sem expor ou reutilizar
// toda a entidade.
public record AtualizarStatusRequest(EnumStatusUsuario status) {
}
