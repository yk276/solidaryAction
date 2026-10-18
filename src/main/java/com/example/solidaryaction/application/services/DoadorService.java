package com.example.solidaryaction.application.services;

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.domain.entities.Doador;
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

    public DoadorResponse salvarDoador(DoadorRequest doador, Long id) {

        if(id == 0L) {

            Doador doadorBanco = new Doador(doador);

            doadorRepository.save(doadorBanco);

            return new DoadorResponse(doadorBanco);
        }else {
            Doador doadorBanco = doadorRepository.findById(id).orElse(null);
            if (doadorBanco != null) {
                doadorBanco.setNome(doador.nome());
                doadorBanco.setCpf(doador.cpf());
                doadorBanco.setEmail(doador.email());
                doadorBanco.setProfissao(doador.profissao());
                doadorBanco.setEndereco(doador.endereco());
                doadorRepository.save(doadorBanco);
                return new DoadorResponse(doadorBanco);
            }
        }
        return null;
    }

    public DoadorResponse buscarPorId(Long id){

        var doador = doadorRepository.findById(id).orElse(null);
        if(doador == null){
            return null;
        }

        return new DoadorResponse(doador);
    }

    public boolean atualizarStatus(EnumStatusDoador status, Long id) {

        Doador doadorBanco = doadorRepository.findById(id).orElse(null);
        if(doadorBanco != null){
            doadorBanco.setStatus(status);
            doadorRepository.save(doadorBanco);
            return true;
        }
        return false;
    }
}
