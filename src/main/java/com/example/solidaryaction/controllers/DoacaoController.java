package com.example.solidaryaction.controllers;


import com.example.solidaryaction.DTOs.AtualizarStatusRequestDoacao;
import com.example.solidaryaction.entities.Doacao;
import com.example.solidaryaction.entities.EnumStatusDoacao;
import com.example.solidaryaction.repository.DoacaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doacoes")
@Tag(name = "Doações", description = "Controller responsável pela gestão das doações do sistema.")
public class DoacaoController {

    @Autowired
    private DoacaoRepository doacaoRepository;


    @GetMapping
    @Operation(summary = "Listar doações", description = "Método responsável por consultar todas as doações registradas.")
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(doacaoRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar doação", description = "Método responsável por criar uma nova doação na plataforma.")
    public ResponseEntity<Doacao> criar(@RequestBody Doacao doacao){

        var doacaoBanco = doacaoRepository.save(doacao);
        return ResponseEntity.ok(doacaoBanco);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar doação por ID", description = "Método responsável por consultar uma doação específica.")
    public ResponseEntity<Doacao> buscarPorId(@PathVariable Long id){

        Doacao doacaoBanco = doacaoRepository.findById(id).orElse(null);
        if(doacaoBanco != null){
            return ResponseEntity.ok(doacaoBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status da doação", description = "Método responsável por alterar o status de uma doação.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequestDoacao statusRequestDoacao){

        Doacao doacaoBanco = doacaoRepository.findById(id).orElse(null);
        if(doacaoBanco != null ){
            doacaoBanco.setStatus(statusRequestDoacao.status());
            doacaoRepository.save(doacaoBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar doação", description = "Método responsável por atualizar os dados de uma doação.")
    public ResponseEntity<Doacao> atualizar(@PathVariable Long id, @RequestBody Doacao doacao){
        try{
            Doacao doacaoBanco = doacaoRepository.findById(id).orElse(null);
            if(doacaoBanco != null) {
                doacaoBanco.setDataDoacao(doacao.getDataDoacao());
                doacaoBanco.setValorDoado(doacao.getValorDoado());
                doacaoBanco.setDescricao(doacao.getDescricao());
                doacaoRepository.save(doacaoBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Excluir doação", description = "Método responsável por excluir uma doação existente do sistema.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Doacao doacaoBanco = doacaoRepository.findById(id).orElse(null);
        if(doacaoBanco != null ){
            doacaoBanco.setStatus(EnumStatusDoacao.EXCLUIDO);
            doacaoRepository.save(doacaoBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

}
