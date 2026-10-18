package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.domain.entities.Doacao;
import com.example.solidaryaction.domain.entities.Doador;
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

    public DoacaoResponse salvarDoacao(DoacaoRequest doacao, Long id) {

        if(id == 0L) {

            Doacao doacaoBanco = new Doacao(doacao);

            doacaoRepository.save(doacaoBanco);

            return new DoacaoResponse(doacaoBanco);
        }else {
            Doacao doacaoBanco = doacaoRepository.findById(id).orElse(null);
            if (doacaoBanco != null) {
                doacaoBanco.setDataDoacao(doacao.dataDoacao());
                doacaoBanco.setValorDoado(doacao.valorDoado());
                doacaoBanco.setDescricao(doacao.descricao());
                doacaoRepository.save(doacaoBanco);
                return new DoacaoResponse(doacaoBanco);
            }
        }
        return null;
    }

    public DoacaoResponse buscarPorId(Long id){

        var doacao = doacaoRepository.findById(id).orElse(null);
        if(doacao == null){
            return null;
        }

        return new DoacaoResponse(doacao);
    }

    public boolean atualizarStatus(EnumStatusDoacao status, Long id) {

        Doacao doacaoBanco = doacaoRepository.findById(id).orElse(null);
        if(doacaoBanco != null){
            doacaoBanco.setStatus(status);
            doacaoRepository.save(doacaoBanco);
            return true;
        }
        return false;
    }
}
