package com.example.solidaryaction.controllers;


import com.example.solidaryaction.DTOs.AtualizarStatusRequestDoador;
import com.example.solidaryaction.entities.Doador;
import com.example.solidaryaction.entities.EnumStatusDoador;
import com.example.solidaryaction.repository.DoadorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doadores")
@Tag(name = "Doadores", description = "Controller responsável pela gestão dos doadores do sistema.")
public class DoadorController {

    @Autowired
    private DoadorRepository doadorRepository;


    @GetMapping
    @Operation(summary = "Listar doadores", description = "Método responsável por consultar todos os doadores cadastrados.")
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(doadorRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar doador", description = "Método responsável por registrar um novo doador no sistema.")
    public ResponseEntity<Doador> criar(@RequestBody Doador doador){

        var doadorBanco = doadorRepository.save(doador);
        return ResponseEntity.ok(doadorBanco);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar doador por ID", description = "Método responsável por consultar um doador específico.")
    public ResponseEntity<Doador> buscarPorId(@PathVariable Long id){

        Doador doadorBanco = doadorRepository.findById(id).orElse(null);
        if(doadorBanco != null){
            return ResponseEntity.ok(doadorBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do doador", description = "Método responsável por alterar o status de um doador.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequestDoador statusRequestDoador){

        Doador doadorBanco = doadorRepository.findById(id).orElse(null);
        if(doadorBanco != null ){
            doadorBanco.setStatus(statusRequestDoador.status());
            doadorRepository.save(doadorBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar doador", description = "Método responsável por atualizar os dados de um doador.")
    public ResponseEntity<Doador> atualizar(@PathVariable Long id, @RequestBody Doador doador){
        try{
            Doador doadorBanco = doadorRepository.findById(id).orElse(null);
            if(doadorBanco != null) {
                doadorBanco.setNome(doador.getNome());
                doadorBanco.setCpf(doador.getCpf());
                doadorBanco.setEmail(doador.getEmail());
                doadorBanco.setEndereco(doador.getEndereco());
                doadorBanco.setProfissao(doador.getProfissao());
                doadorRepository.save(doadorBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Excluir doador", description = "Método responsável por excluir um doador existente do sistema.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Doador doadorBanco = doadorRepository.findById(id).orElse(null);
        if(doadorBanco != null ){
            doadorBanco.setStatus(EnumStatusDoador.EXCLUIDO);
            doadorRepository.save(doadorBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

}
