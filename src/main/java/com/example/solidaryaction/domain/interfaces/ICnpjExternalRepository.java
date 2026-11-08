package com.example.solidaryaction.domain.interfaces;

import com.example.solidaryaction.infra.dto.BrasilApiCnpjResponse;

public interface ICnpjExternalRepository {

    BrasilApiCnpjResponse buscarCnpj(String cnpj);

}