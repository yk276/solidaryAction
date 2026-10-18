package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.domain.entities.Campanha;
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

    public CampanhaResponse salvarCampanha(CampanhaRequest campanha, Long id) {

        if(id == 0L) {

            Campanha campanhaBanco = new Campanha(campanha);

            campanhaRepository.save(campanhaBanco);

            return new CampanhaResponse(campanhaBanco);
        }else {
            Campanha campanhaBanco = campanhaRepository.findById(id).orElse(null);
            if (campanhaBanco != null) {
                campanhaBanco.setTitulo(campanha.titulo());
                campanhaBanco.setDescricao(campanha.descricao());
                campanhaBanco.setMetaFinanceira(campanha.metaFinanceira());
                campanhaBanco.setTotalArrecadado(campanha.totalArrecadado());
                campanhaBanco.setDataInicio(campanha.dataInicio());
                campanhaBanco.setDataFim(campanha.dataFim());
                campanhaRepository.save(campanhaBanco);
                return new CampanhaResponse(campanhaBanco);
            }
        }
        return null;
    }

    public CampanhaResponse buscarPorId(Long id){

        var campanha = campanhaRepository.findById(id).orElse(null);
        if(campanha == null){
            return null;
        }

        return new CampanhaResponse(campanha);
    }

    public boolean atualizarStatus(EnumStatusCampanha status, Long id) {

        Campanha campanhaBanco = campanhaRepository.findById(id).orElse(null);
        if(campanhaBanco != null){
            campanhaBanco.setStatus(status);
            campanhaRepository.save(campanhaBanco);
            return true;
        }
        return false;
    }
}
