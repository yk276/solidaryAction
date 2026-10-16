package com.example.solidaryaction.application.DTOs;

import com.example.solidaryaction.domain.entities.Doador;

public record DoadorResponse(Long id, String nome, String email, EnumStatusDoador status) {

    public DoadorResponse(Doador doadorEntidade) {

        this(
                doadorEntidade.getId(),
                doadorEntidade.getNome(),
                doadorEntidade.getEmail(),
                doadorEntidade.getStatus()
        );
    }
}
