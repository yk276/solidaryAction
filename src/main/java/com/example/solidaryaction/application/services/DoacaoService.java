package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.CampanhaResponse;
import com.example.solidaryaction.application.DTOs.DoacaoResponse;
import com.example.solidaryaction.domain.repository.DoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoacaoService {

    @Autowired
    private DoacaoRepository doacaoRepository;

    public List<DoacaoResponse> listarTodasDoacoesTable(){

        return  doacaoRepository.findAll()
                .stream()
                .map(DoacaoResponse::new)
                .toList();
    }
}
