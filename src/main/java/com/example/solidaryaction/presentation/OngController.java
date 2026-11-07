package com.example.solidaryaction.presentation;

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.application.services.OngService;
import com.example.solidaryaction.application.services.UsuarioService;
import com.example.solidaryaction.domain.repository.OngRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Recebe as requisições relacionadas às ONGs.
@RestController
@RequestMapping("/ongs")
@Tag(
        name = "ONGs",
        description = "Controller responsável pela gestão das ONGs do sistema."
)
public class OngController {

    @Autowired
    private OngRepository ongRepository;

    @Autowired
    private OngService ongService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(
            summary = "Listar ONGs",
            description = "Método responsável por consultar as ONGs permitidas."
    )
    public ResponseEntity<List<OngResponse>> listarTodos() {

        return ResponseEntity.ok(
                ongService.listarTodasOngsTable()
        );
    }

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar ONG",
            description = "Método responsável por registrar uma nova ONG."
    )
    public ResponseEntity<?> criar(@RequestBody OngRequest ong) {

        try {
            OngResponse ongBanco = ongService.salvarOng(ong, 0L);

            return ResponseEntity.ok(ongBanco);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/admin")
    @Operation(
            summary = "Criar ONG admin",
            description = "Método responsável por registrar uma nova ONG admin."
    )
    public ResponseEntity<CriarOngAdminResponse> criarOngAdmin(
            @RequestBody CriarOngAdminRequest criarOngAdminRequest) {

        try {
            CriarOngAdminResponse respostaSalvar =
                    ongService.criarOngAdmin(criarOngAdminRequest);

            return ResponseEntity.ok(respostaSalvar);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //Recebe o cadastro conjunto enviado pelo desktop.
    @PostMapping("/cadastro-com-coordenador")
    @Operation(
            summary = "Cadastrar ONG com coordenador",
            description = "Cadastra uma ONG e uma nova conta de coordenador vinculada a ela."
    )
    public ResponseEntity<?> cadastrarOngComCoordenador(
            @RequestBody CadastroOngCoordenadorRequest cadastro) {

        try {
            OngResponse ong = ongService.cadastrarOngComCoordenador(
                    cadastro
            );

            return ResponseEntity.ok(ong);

        } catch (org.springframework.security.access.AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar ONG por ID",
            description = "Método responsável por consultar uma ONG específica."
    )
    public ResponseEntity<OngResponse> buscarPorId(
            @PathVariable Long id) {

        OngResponse ongBanco = ongService.buscarPorId(id);

        if (ongBanco != null) {
            return ResponseEntity.ok(ongBanco);
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Atualizar status da ONG",
            description = "Método responsável por alterar o status de uma ONG."
    )
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long id,
            @RequestBody AtualizarStatusRequestOng statusRequestOng) {

        var atualizarOng = ongService.atualizarStatus(
                statusRequestOng.status(), id
        );

        if (atualizarOng) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar ONG",
            description = "Método responsável por atualizar os dados de uma ONG."
    )
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody OngRequest ong) {

        try {
            OngResponse ongBanco = ongService.salvarOng(ong, id);

            if (ongBanco == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(ongBanco);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(
            summary = "Excluir ONG",
            description = "Método responsável por aplicar a exclusão lógica de uma ONG."
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        var atualizarOng = ongService.atualizarStatus(
                EnumStatusOng.EXCLUIDO, id
        );

        if (atualizarOng) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{ongId}/coordenadores")
    public ResponseEntity<?> criarCoordenador(
            @PathVariable Long ongId,
            @RequestBody UsuarioRequest usuario) {

        try {
            UsuarioResponse coordenador = usuarioService.criarCoordenador(
                    ongId, usuario
            );

            if (coordenador == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(coordenador);

        } catch (org.springframework.security.access.AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}