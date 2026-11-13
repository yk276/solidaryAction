package com.example.solidaryaction.application.DTOs;

import com.example.solidaryaction.domain.entities.Campa;

import java.math.BigDecimal;

public record CampanhaResponse(Long id, String titulo, String descricao, BigDecimal metaFinanceira, BigDecimal totalArrecadado, String dataInicio, String dataFim, EnumStatusCampanha status) {

    public CampanhaResponse(Campa campanhaEntidade){

        this(
                campanhaEntidade.getId(),
                campanhaEntidade.getTitulo(),
                campanhaEntidade.getDescricao(),
                campanhaEntidade.getMetaFinanceira(),
                campanhaEntidade.getTotalArrecadado(),
                campanhaEntidade.getDataInicio(),
                campanhaEntidade.getDataFim(),
                campanhaEntidade.getStatus()
        );
    }
}
