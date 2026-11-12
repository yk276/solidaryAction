package com.example.solidaryaction.presentation;

import com.example.solidaryaction.application.DTOs.CnpjConsultaResponse;
import com.example.solidaryaction.application.services.CnpjService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cnpj")
@Tag(
        name = "Consulta de CNPJ",
        description = "Consulta dados cadastrais pelo CNPJ em uma API externa."
)
public class CnpjController {

    @Autowired
    private CnpjService cnpjService;

    @GetMapping("/{cnpj}")
    @Operation(
            summary = "Consultar CNPJ",
            description = "Retorna o CNPJ, a razão social e o nome fantasia."
    )
    public ResponseEntity<?> buscarCnpj(
            @PathVariable String cnpj) {

        try {
            CnpjConsultaResponse resultado = cnpjService.buscarCnpj(cnpj);

            return ResponseEntity.ok(resultado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(e.getMessage());
        }
    }
}