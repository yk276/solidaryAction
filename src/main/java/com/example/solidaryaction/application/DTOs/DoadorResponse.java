package com.example.solidaryaction.application.DTOs;

import com.example.solidaryaction.domain.entities.Doador;

public record DoadorResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        String profissao,
        EnumStatusDoador status
) {

    public DoadorResponse(Doador doadorEntidade) {
        this(
                doadorEntidade.getId(),
                doadorEntidade.getNome(),
                doadorEntidade.getCpf() != null
                        ? doadorEntidade.getCpf().toString()
                        : null,
                doadorEntidade.getEmail(),
                doadorEntidade.getProfissao(),
                doadorEntidade.getStatus()
        );
    }
}