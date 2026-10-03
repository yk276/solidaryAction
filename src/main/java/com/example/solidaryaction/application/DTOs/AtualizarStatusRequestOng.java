package com.example.solidaryaction.application.DTOs;

import com.example.solidaryaction.domain.entities.EnumStatusOng;

// Este DTO transporta apenas os dados necessários para uma requisição/resposta específica, sem expor ou reutilizar
// toda a entidade.
public record AtualizarStatusRequestOng(EnumStatusOng status) {
}
