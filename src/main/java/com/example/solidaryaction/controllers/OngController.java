package com.example.solidaryaction.controllers;


import com.example.solidaryaction.entities.Ong;
import com.example.solidaryaction.repository.OngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ongs")
public class OngController {

    @Autowired
    private OngRepository ongRepository;

    @GetMapping
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(ongRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Ong> criar(@RequestBody Ong ong){

        var ongBanco = ongRepository.save(ong);
        return ResponseEntity.ok(ongBanco);

    }


}
