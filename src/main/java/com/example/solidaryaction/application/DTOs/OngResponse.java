package com.example.solidaryaction.application.DTOs;

import com.example.solidaryaction.domain.entities.Ong;

import java.math.BigDecimal;

public record OngResponse(Long id, String nomeFantasia, String email, BigDecimal totalArrecadado, EnumStatusOng status) {

    public OngResponse(Ong ongEntidade){

        this(
                ongEntidade.getId(),
                ongEntidade.getNomeFantasia(),
                ongEntidade.getEmail(),
                ongEntidade.getTotalArrecadado(),
                ongEntidade.getStatus()
        );
    }
}
