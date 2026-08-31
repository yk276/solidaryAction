package com.example.solidaryaction.controllers;


import com.example.solidaryaction.DTOs.AtualizarStatusRequestOng;
import com.example.solidaryaction.entities.EnumStatusOng;
import com.example.solidaryaction.entities.Ong;
import com.example.solidaryaction.repository.OngRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ongs")
@Tag(name = "ONGs", description = "Controller responsável pela gestão das ONGs do sistema.")
public class OngController {

    @Autowired
    private OngRepository ongRepository;


    @GetMapping
    @Operation(summary = "Listar ONGs", description = "Método responsável por consultar todas as ONGs cadastradas.")
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(ongRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar ONG", description = "Método responsável por registrar uma nova ONG no sistema.")
    public ResponseEntity<Ong> criar(@RequestBody Ong ong){

        var ongBanco = ongRepository.save(ong);
        return ResponseEntity.ok(ongBanco);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ONG por ID", description = "Método responsável por consultar uma ONG específica")
    public ResponseEntity<Ong> buscarPorId(@PathVariable Long id){

        Ong ongBanco = ongRepository.findById(id).orElse(null);
        if(ongBanco != null){
            return ResponseEntity.ok(ongBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status da ONG", description = "Método responsável por alterar o status de uma ONG.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequestOng statusRequestOng){

        Ong ongBanco = ongRepository.findById(id).orElse(null);
        if(ongBanco != null ){
            ongBanco.setStatus(statusRequestOng.status());
            ongRepository.save(ongBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ONG", description = "Método responsável por atualizar os dados de uma ONG.")
    public ResponseEntity<Ong> atualizar(@PathVariable Long id, @RequestBody Ong ong){
        try{
            Ong ongBanco = ongRepository.findById(id).orElse(null);
            if(ongBanco != null) {
                ongBanco.setNomeFantasia(ong.getNomeFantasia());
                ongBanco.setRazaoSocial(ong.getRazaoSocial());
                ongBanco.setCnpj(ong.getCnpj());
                ongBanco.setEmail(ong.getEmail());
                ongBanco.setEndereco(ong.getEndereco());
                ongBanco.setTotalArrecadado(ong.getTotalArrecadado());
                ongRepository.save(ongBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Excluir ONG", description = "Método responsável por excluir uma ONG existente do sistema.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Ong ongBanco = ongRepository.findById(id).orElse(null);
        if(ongBanco != null ){
            ongBanco.setStatus(EnumStatusOng.EXCLUIDO);
            ongRepository.save(ongBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

}
