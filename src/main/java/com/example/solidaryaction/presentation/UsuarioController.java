package com.example.solidaryaction.presentation;

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.application.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(
        name = "Usuários",
        description = "Controller responsável pela gestão de usuários do sistema."
)
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar usuários")
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {

        return ResponseEntity.ok(
                usuarioService.listarTodosUsuariosTable()
        );
    }

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar usuário")
    public ResponseEntity<?> criar(
            @RequestBody UsuarioRequest usuario) {

        try {
            UsuarioResponse usuarioBanco =
                    usuarioService.salvarUsuario(usuario, 0L);

            if (usuarioBanco == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(usuarioBanco);

        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<CriarAdminResponse> criarAdmin(
            @RequestBody CriarAdminRequest criarAdminRequest) {

        try {
            CriarAdminResponse respostaSalvar =
                    usuarioService.criarAdmin(criarAdminRequest);

            return ResponseEntity.ok(respostaSalvar);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<UsuarioResponse> buscarPorId(
            @PathVariable Long id) {

        UsuarioResponse usuarioBanco =
                usuarioService.buscarPorId(id);

        if (usuarioBanco != null) {
            return ResponseEntity.ok(usuarioBanco);
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do usuário")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long id,
            @RequestBody AtualizarStatusRequest statusRequest) {

        var atualizarUsuario = usuarioService.atualizarStatus(
                statusRequest.status(), id
        );

        if (atualizarUsuario) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRequest usuario) {

        try {
            UsuarioResponse usuarioBanco =
                    usuarioService.salvarUsuario(usuario, id);

            if (usuarioBanco == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(usuarioBanco);

        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Excluir usuário")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        var atualizarUsuario = usuarioService.atualizarStatus(
                EnumStatusUsuario.EXCLUIDO, id
        );

        if (atualizarUsuario) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar usuário")
    public ResponseEntity<?> cadastrar(
            @RequestBody UsuarioRequest cadastroRequest) {

        try {
            UsuarioResponse usuarioBanco =
                    usuarioService.salvarUsuario(cadastroRequest, 0L);

            if (usuarioBanco == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(usuarioBanco);

        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}