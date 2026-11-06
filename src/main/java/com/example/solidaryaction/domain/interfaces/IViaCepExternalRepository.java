package com.example.solidaryaction.domain.interfaces;

import com.example.solidaryaction.infra.dto.ViaCepResponse;

public interface IViaCepExternalRepository {

    ViaCepResponse buscarEnderecoViaCepPorCep(String cep);
}
