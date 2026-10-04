package com.example.solidaryaction.application.DTOs;

import com.example.solidaryaction.domain.entities.Campanha;
import com.example.solidaryaction.domain.entities.EnumStatusCampanha;
import java.math.BigDecimal;

public record CampanhaResponse(Long id, String titulo, String descricao, BigDecimal metaFinanceira, BigDecimal totalArrecadado, String dataInicio, String dataFim, EnumStatusCampanha status) {

    public CampanhaResponse(Campanha campanhaEntidade){

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
