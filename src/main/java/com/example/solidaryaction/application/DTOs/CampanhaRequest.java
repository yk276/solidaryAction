package com.example.solidaryaction.application.DTOs;

import java.math.BigDecimal;

public record CampanhaRequest(String titulo, String descricao, BigDecimal metaFinanceira, BigDecimal totalArrecadado, String dataInicio, String dataFim) {
}
