package com.example.solidaryaction.application.DTOs;

import com.example.solidaryaction.domain.entities.Ong;

import java.math.BigDecimal;

public record OngResponse(
        Long id,
        String nomeFantasia,
        String razaoSocial,
        String cnpj,
        String email,
        String endereco,
        BigDecimal totalArrecadado,
        EnumStatusOng status
) {

    public OngResponse(Ong ongEntidade) {
        this(
                ongEntidade.getId(),
                ongEntidade.getNomeFantasia(),
                ongEntidade.getRazaoSocial(),
                ongEntidade.getCnpj().toString(),
                ongEntidade.getEmail(),
                ongEntidade.getEndereco(),
                ongEntidade.getTotalArrecadado(),
                ongEntidade.getStatus()
        );
    }
}