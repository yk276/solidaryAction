package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.DoacaoResponse;
import com.example.solidaryaction.application.DTOs.DoadorResponse;
import com.example.solidaryaction.domain.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoadorService {

    @Autowired
    private DoadorRepository doadorRepository;

    public List<DoadorResponse> listarTodasDoadoresTable(){

        return  doadorRepository.findAll()
                .stream()
                .map(DoadorResponse::new)
                .toList();
    }
}
