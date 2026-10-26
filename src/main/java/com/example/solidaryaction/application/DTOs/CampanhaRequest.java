package com.example.solidaryaction.application.DTOs;

import java.math.BigDecimal;

public record CampanhaRequest(
        String titulo,
        String descricao,
        BigDecimal metaFinanceira,
        String dataInicio,
        String dataFim
) {
}