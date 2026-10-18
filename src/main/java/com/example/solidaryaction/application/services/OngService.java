package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.domain.entities.Ong;
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

    public OngResponse salvarOng(OngRequest ong, Long id) {

        if(id == 0L) {

            Ong ongBanco = new Ong(ong);

            ongRepository.save(ongBanco);

            return new OngResponse(ongBanco);
        }else {
            Ong ongBanco = ongRepository.findById(id).orElse(null);
            if (ongBanco != null) {
                ongBanco.setNomeFantasia(ong.nomeFantasia());
                ongBanco.setCnpj(ong.cnpj());
                ongBanco.setEmail(ong.email());
                ongBanco.setEndereco(ong.endereco());
                ongBanco.setTotalArrecadado(ong.totalArrecadado());
                ongRepository.save(ongBanco);
                return new OngResponse(ongBanco);
            }
        }
        return null;
    }

    public OngResponse buscarPorId(Long id){

        var ong = ongRepository.findById(id).orElse(null);
        if(ong == null){
            return null;
        }

        return new OngResponse(ong);
    }

    public boolean atualizarStatus(EnumStatusOng status, Long id) {

        Ong ongBanco = ongRepository.findById(id).orElse(null);
        if(ongBanco != null){
            ongBanco.setStatus(status);
            ongRepository.save(ongBanco);
            return true;
        }
        return false;
    }
}
