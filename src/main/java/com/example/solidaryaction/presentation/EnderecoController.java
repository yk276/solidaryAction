package com.example.solidaryaction.presentation;

import com.example.solidaryaction.application.DTOs.EnderecoResponse;
import com.example.solidaryaction.application.services.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endereco")
@Tag(name = "Endereços", description = "Controller responsável pela gestão dos endereços.")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/{cep}")
    @Operation(summary = "Buscar endereço", description = "Método responsável por buscar endereços.")
    public EnderecoResponse buscarEndereco(@PathVariable String cep){
        return enderecoService.BuscarEndereco(cep);
    }
}
