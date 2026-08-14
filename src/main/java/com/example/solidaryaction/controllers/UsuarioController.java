package com.example.solidaryaction.controllers;

import com.example.solidaryaction.entities.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @GetMapping
    public ResponseEntity<?> listarTodos(){

        List<Usuario> usuarios =
                List.of(new Usuario(1L,
                        "Yohanna",
                        "09717745951",
                        "123456",
                        "yohanna@gmail.com"));

        return ResponseEntity.ok(usuarios);
    }

}
