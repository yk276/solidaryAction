package com.example.solidaryaction.controllers;


import com.example.solidaryaction.entities.Doador;
import com.example.solidaryaction.repository.DoadorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doadores")
@Tag(name = "Doador", description = "Grupos de Doadores")
public class DoadorController {

    @Autowired
    private DoadorRepository doadorRepository;

    @Operation(summary = "Metodo de consulta de lista de doadores!", description = "Metodo responsavel em efetuar a consulta de todos os doadores")
    @GetMapping
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(doadorRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Doador> criar(@RequestBody Doador doador){

        var doadorBanco = doadorRepository.save(doador);
        return ResponseEntity.ok(doadorBanco);

    }


}
