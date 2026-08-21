package com.example.solidaryaction.controllers;


import com.example.solidaryaction.entities.Doacao;
import com.example.solidaryaction.repository.DoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doacoes")
public class DoacaoController {

    @Autowired
    private DoacaoRepository doacaoRepository;

    @GetMapping
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(doacaoRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Doacao> criar(@RequestBody Doacao doacao){

        var doacaoBanco = doacaoRepository.save(doacao);
        return ResponseEntity.ok(doacaoBanco);

    }


}
