package com.example.solidaryaction.application.DTOs;

import com.example.solidaryaction.domain.entities.Doacao;

import java.math.BigDecimal;

public record DoacaoResponse(Long id, String dataDoacao, BigDecimal valorDoado, String descricao, EnumStatusDoacao status) {

    public DoacaoResponse(Doacao doacaoEntidade) {

        this(
                doacaoEntidade.getId(),
                doacaoEntidade.getDataDoacao(),
                doacaoEntidade.getValorDoado(),
                doacaoEntidade.getDescricao(),
                doacaoEntidade.getStatus()
        );
    }
}
