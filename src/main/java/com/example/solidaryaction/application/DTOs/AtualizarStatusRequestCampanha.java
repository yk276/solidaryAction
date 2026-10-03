package com.example.solidaryaction.application.DTOs;

import com.example.solidaryaction.domain.entities.EnumStatusCampanha;

// Este DTO transporta apenas os dados necessários para uma requisição/resposta específica, sem expor ou reutilizar
// toda a entidade.
public record AtualizarStatusRequestCampanha(EnumStatusCampanha status) {
}
