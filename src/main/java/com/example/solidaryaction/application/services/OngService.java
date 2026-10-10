package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.domain.entities.Ong;
import com.example.solidaryaction.domain.entities.Usuario;
import com.example.solidaryaction.domain.repository.OngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OngService {

    @Autowired
    OngRepository ongRepository;

    @Value("${spring.secret}")
    private String secret;

    public List<OngResponse> listarTodasOngsTable(){

        return  ongRepository.findAll()
                .stream()
                .map(OngResponse::new)
                .toList();
    }

    public CriarOngAdminResponse criarOngAdmin(CriarOngAdminRequest criarOngAdminRequest) {

        if(!criarOngAdminRequest.secretKey().equals(secret)){
            return new CriarOngAdminResponse(0L, "Ong salva com sucesso!");
        }

        Ong ongAdminSalvar = new Ong(criarOngAdminRequest);

        ongRepository.save(ongAdminSalvar);

        return new CriarOngAdminResponse(ongAdminSalvar.getId(), "Ong salva com sucesso!");
    }
}
