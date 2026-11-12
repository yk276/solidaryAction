package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.CnpjConsultaResponse;

import com.example.solidaryaction.domain.interfaces.ICnpjExternalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CnpjService {

    @Autowired
    private ICnpjExternalRepository cnpjExternalRepository;

    public CnpjConsultaResponse buscarCnpj(String cnpj) {

        var resultado = cnpjExternalRepository.buscarCnpj(cnpj);

        return new CnpjConsultaResponse(
                resultado.getCnpj(),
                resultado.getRazaoSocial(),
                resultado.getNomeFantasia()
        );
    }
}