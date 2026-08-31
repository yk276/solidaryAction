package com.example.solidaryaction.controllers;

import com.example.solidaryaction.DTOs.AtualizarStatusRequestCampanha;
import com.example.solidaryaction.entities.Campanha;
import com.example.solidaryaction.entities.EnumStatusCampanha;
import com.example.solidaryaction.repository.CampanhaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campanhas")
@Tag(name = "Campanhas", description = "Controller responsável pela gestão das campanhas do sistema.")
public class CampanhaController {

    @Autowired
    private CampanhaRepository campanhaRepository;

    @GetMapping
    @Operation(summary = "Listar campanhas", description = "Método responsável por consultar todas as campanhas cadastradas.")
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(campanhaRepository.findAll());
    }


    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar campanha", description = "Método responsável por registrar uma nova campanha no sistema.")
    public ResponseEntity<Campanha> criar(@RequestBody Campanha campanha){

        var campanhaBanco = campanhaRepository.save(campanha);
        return ResponseEntity.ok(campanhaBanco);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar campanha por ID", description = "Método responsável por consultar uma campanha específica.")
    public ResponseEntity<Campanha> buscarPorId(@PathVariable Long id){

        Campanha campanhaBanco = campanhaRepository.findById(id).orElse(null);
        if(campanhaBanco != null){
            return ResponseEntity.ok(campanhaBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status da campanha", description = "Método responsável por alterar o status de uma campanha.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequestCampanha statusRequestCampanha){

        Campanha campanhaBanco = campanhaRepository.findById(id).orElse(null);
        if(campanhaBanco != null ){
            campanhaBanco.setStatus(statusRequestCampanha.status());
            campanhaRepository.save(campanhaBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar campanha", description = "Método responsável por atualizar os dados de uma campanha.")
    public ResponseEntity<Campanha> atualizar(@PathVariable Long id, @RequestBody Campanha campanha){
        try{
            Campanha campanhaBanco = campanhaRepository.findById(id).orElse(null);
            if(campanhaBanco != null) {
                campanhaBanco.setStatus(campanha.getStatus());
                campanhaBanco.setTitulo(campanha.getTitulo());
                campanhaBanco.setDescricao(campanha.getDescricao());
                campanhaBanco.setMetaFinanceira(campanha.getMetaFinanceira());
                campanhaBanco.setTotalArrecadado(campanha.getTotalArrecadado());
                campanhaBanco.setDataInicio(campanha.getDataInicio());
                campanhaBanco.setDataFim(campanha.getDataFim());
                campanhaRepository.save(campanhaBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Excluir campanha", description = "Método responsável por excluir uma campanha existente do sistema.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Campanha campanhaBanco = campanhaRepository.findById(id).orElse(null);
        if(campanhaBanco != null ){
            campanhaBanco.setStatus(EnumStatusCampanha.EXCLUIDO);
            campanhaRepository.save(campanhaBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

}
