package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.CampanhaResponse;
import com.example.solidaryaction.domain.repository.CampanhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampanhaService {

    @Autowired
    private CampanhaRepository campanhaRepository;

    public List<CampanhaResponse> listarTodasCampanhasTable(){

        return  campanhaRepository.findAll()
                .stream()
                .map(CampanhaResponse::new)
                .toList();
    }
}
