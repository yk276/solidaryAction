package com.example.solidaryaction.controllers;


import com.example.solidaryaction.entities.Doador;
import com.example.solidaryaction.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doadores")
public class DoadorController {

    @Autowired
    private DoadorRepository doadorRepository;

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
