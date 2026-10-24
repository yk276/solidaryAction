package com.example.solidaryaction.presentation;

//Controller REST de Usuario. Recebe requisições HTTP, chama o Repository correspondente, monta
//respostas HTTP e documenta os endpoints para o Swagger.

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.application.services.UsuarioService;
import com.example.solidaryaction.application.DTOs.EnumStatusUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Este controller recebe requisições HTTP desta entidade. @RequestMapping define a rota base e o Swagger usa
//@Tag/@Operation para documentar os endpoints.
@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Controller responsável pela gestão de usuários do sistema.")
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;

    //Endpoint GET: consulta dados. O método usa o Repository para buscar no banco e devolve a resposta HTTP ao
    //frontend/Swagger.
    @GetMapping
    @Operation(summary = "Listar usuários", description = "Método responsável por consultar todos os usuários cadastrados.")
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {

        return ResponseEntity.ok(usuarioService.listarTodosUsuariosTable());
    }


    //Endpoint POST: recebe um objeto no corpo da requisição e salva um novo registro usando o Repository.
    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar usuário", description = "Método responsável por registrar um novo usuário no sistema.")
    public ResponseEntity<?> criar(@RequestBody UsuarioRequest usuario) {

        try {
            UsuarioResponse usuarioBanco = usuarioService.salvarUsuario(usuario,0L);
            return ResponseEntity.ok(usuarioBanco);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<CriarAdminResponse> criarAdmin(@RequestBody CriarAdminRequest criarAdminRequest) {

        try {
            CriarAdminResponse respostaSalvar = usuarioService.criarAdmin(criarAdminRequest);
            return ResponseEntity.ok(respostaSalvar);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    //Endpoint GET por ID: recebe o identificador pela URL, procura o registro no Repository e retorna 200 quando
    //encontra ou 404 quando não encontra.
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Método responsável por consultar um usuário específico pelo seu identificador.")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {

        UsuarioResponse usuarioBanco = usuarioService.buscarPorId(id);
        if (usuarioBanco != null) {
            return ResponseEntity.ok(usuarioBanco);
        }
        return ResponseEntity.notFound().build();
    }

    //Endpoint PATCH: altera somente o status do registro, sem substituir todos os demais campos.
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do usuário", description = "Método responsável por alterar o status do usuário.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest) {

        var atualizarUsuario = usuarioService.atualizarStatus(statusRequest.status(),id);

        if(atualizarUsuario){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Endpoint PUT: busca o registro existente, atualiza seus campos e salva novamente no banco.
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Método responsável por atualizar os dados do um usuário.")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody UsuarioRequest usuario) {
        try {
            UsuarioResponse usuarioBanco = usuarioService.salvarUsuario(usuario,id);

            if(usuarioBanco == null){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(usuarioBanco);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Endpoint de exclusão: localiza o registro e aplica a lógica de exclusão definida pelo projeto antes de salvar a
    //alteração.
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Excluir usuário", description = "Método responsável por excluir um usuário existente do sistema.")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        var atualizarUsuario = usuarioService.atualizarStatus(EnumStatusUsuario.EXCLUIDO,id);

        if(atualizarUsuario){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

   // Endpoint POST: recebe um objeto no corpo da requisição e salva um novo registro usando o Repository.
    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar usuário", description = "Método responsável por efetuar o cadastro de um novo usuário.")
    public ResponseEntity<?> cadastrar(@RequestBody UsuarioRequest cadastroRequest){

        try {
            UsuarioResponse usuarioBanco = usuarioService.salvarUsuario(cadastroRequest,0L);
            return ResponseEntity.ok(usuarioBanco);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/matricular")
    public ResponseEntity<?> matricular(@PathVariable Long id, @RequestBody MatriculaRequest){

        var retornoMatricula = usuarioService.matricularAluno(id,matriculaRequest);

        if(retornoMatricula){
            return ResponseEntity
        }

    }

}