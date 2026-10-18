package com.example.solidaryaction.application.DTOs;

import java.math.BigDecimal;

public record DoacaoRequest(String dataDoacao, BigDecimal valorDoado, String descricao) {
}
