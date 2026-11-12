package com.example.solidaryaction.domain.interfaces;

import com.example.solidaryaction.infra.dto.CnpjResponse;

public interface ICnpjExternalRepository {

    CnpjResponse buscarCnpj(String cnpj);

}