package com.example.solidaryaction.controllers;

import com.example.solidaryaction.DTOs.AtualizarStatusRequest;
import com.example.solidaryaction.DTOs.CadastroRequest;
import com.example.solidaryaction.entities.EnumStatusUsuario;
import com.example.solidaryaction.entities.Usuario;
import com.example.solidaryaction.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Controller responsável pela gestão de usuários do sistema.")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Método responsável por consultar todos os usuários cadastrados.")
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(usuarioRepository.findAll());
    }


    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar usuário", description = "Método responsável por registrar um novo usuário no sistema.")
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario){

        var usuarioBanco = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioBanco);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Método responsável por consultar um usuário específico pelo seu identificador.")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco != null){
            return ResponseEntity.ok(usuarioBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do usuário", description = "Método responsável por alterar o status do usuário.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest){

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco != null ){
            usuarioBanco.setStatus(statusRequest.status());
            usuarioRepository.save(usuarioBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Método responsável por atualizar os dados do um usuário.")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario){
        try{
            Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
            if(usuarioBanco != null) {
                usuarioBanco.setStatus(usuario.getStatus());
                usuarioBanco.setNome(usuario.getNome());
                usuarioBanco.setCpf(usuario.getCpf());
                usuarioBanco.setEmail(usuario.getEmail());
                usuarioBanco.setSenha(usuario.getSenha());
                usuarioRepository.save(usuarioBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Excluir usuário", description = "Método responsável por excluir um usuário existente do sistema.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco != null ){
            usuarioBanco.setStatus(EnumStatusUsuario.EXCLUIDO);
            usuarioRepository.save(usuarioBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar usuário", description = "Método responsável por efetuar o cadastro de um novo usuário.")
    public ResponseEntity<Usuario> cadastrar(@RequestBody CadastroRequest cadastroRequest){

        Usuario usuario = new Usuario();
        usuario.setNome(cadastroRequest.nome());
        usuario.setCpf(cadastroRequest.cpf());
        usuario.setEmail(cadastroRequest.email());
        var usuarioBanco = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioBanco);
    }
}