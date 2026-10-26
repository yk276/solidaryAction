package com.example.solidaryaction.application.DTOs;

import com.example.solidaryaction.domain.entities.Doacao;

import java.math.BigDecimal;

public record DoacaoResponse(
        Long id,
        String dataDoacao,
        BigDecimal valorDoado,
        String descricao,
        EnumStatusDoacao status,
        Long campanhaId,
        Long doadorId,
        boolean metaAtingida,
        String mensagem
) {

    public DoacaoResponse(Doacao doacaoEntidade) {
        this(
                doacaoEntidade.getId(),
                doacaoEntidade.getDataDoacao(),
                doacaoEntidade.getValorDoado(),
                doacaoEntidade.getDescricao(),
                doacaoEntidade.getStatus(),
                doacaoEntidade.getCampanha().getId(),
                doacaoEntidade.getDoador().getId(),
                doacaoEntidade.getCampanha().isMetaAtingida(),
                doacaoEntidade.getCampanha().isMetaAtingida()
                        ? "A campanha atingiu a meta financeira!"
                        : "A campanha ainda não atingiu a meta financeira."
        );
    }
}