package com.example.solidaryaction.controllers;


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
@Tag(name = "Ongs", description = "Grupos de Ong'S")
public class OngController {

    @Autowired
    private OngRepository ongRepository;


    @GetMapping
    @Operation(summary = "Metodo de consulta de lista de ongs!", description = "Metodo responsavel em efetuar a consulta de todos as Ongs")
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(ongRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Metodo de criação de ongs!", description = "Metodo responsável em efetuar a criação de novas ongs!")
    public ResponseEntity<Ong> criar(@RequestBody Ong ong){

        var ongBanco = ongRepository.save(ong);
        return ResponseEntity.ok(ongBanco);

    }


}
