package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.CampanhaResponse;
import com.example.solidaryaction.application.DTOs.OngResponse;
import com.example.solidaryaction.domain.repository.OngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OngService {

    @Autowired
    OngRepository ongRepository;

    public List<OngResponse> listarTodasOngsTable(){

        return  ongRepository.findAll()
                .stream()
                .map(OngResponse::new)
                .toList();
    }
}
