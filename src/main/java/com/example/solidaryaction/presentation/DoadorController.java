package com.example.solidaryaction.presentation;

//Controller REST de Doador. Recebe requisições HTTP, chama o Repository correspondente, monta
//respostas HTTP e documenta os endpoints para o Swagger.

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.application.services.DoadorService;
import com.example.solidaryaction.domain.repository.DoadorRepository;
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
@RequestMapping("/doadores")
@Tag(name = "Doadores", description = "Controller responsável pela gestão dos doadores do sistema.")
public class DoadorController {

    @Autowired
    private DoadorRepository doadorRepository;

    @Autowired
    private DoadorService doadorService;

    //Endpoint GET: consulta dados. O método usa o Repository para buscar no banco e devolve a resposta HTTP ao
    // frontend/Swagger.
    @GetMapping
    @Operation(summary = "Listar doadores", description = "Método responsável por consultar todos os doadores cadastrados.")
    public ResponseEntity<List<DoadorResponse>> listarTodos(){

        return  ResponseEntity.ok(doadorService.listarTodosDoadoresTable());
    }

    //Endpoint POST: recebe um objeto no corpo da requisição e salva um novo registro usando o Repository.
    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar doador", description = "Método responsável por registrar um novo doador no sistema.")
    public ResponseEntity<?> criar(@RequestBody DoadorRequest doador){

        try {
            DoadorResponse doadorBanco = doadorService.salvarDoador(doador,0L);
            return ResponseEntity.ok(doadorBanco);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    //Endpoint GET por ID: recebe o identificador pela URL, procura o registro no Repository e retorna 200 quando
    //encontra ou 404 quando não encontra.
    @GetMapping("/{id}")
    @Operation(summary = "Buscar doador por ID", description = "Método responsável por consultar um doador específico.")
    public ResponseEntity<DoadorResponse> buscarPorId(@PathVariable Long id){

        DoadorResponse doadorBanco = doadorService.buscarPorId(id);
        if(doadorBanco != null){
            return ResponseEntity.ok(doadorBanco);
        }
        return ResponseEntity.notFound().build();
    }

    //Endpoint PATCH: altera somente o status do registro, sem substituir todos os demais campos.
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do doador", description = "Método responsável por alterar o status de um doador.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequestDoador statusRequestDoador){

        var atualizarDoador = doadorService.atualizarStatus(statusRequestDoador.status(),id);

        if(atualizarDoador){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Endpoint PUT: busca o registro existente, atualiza seus campos e salva novamente no banco.
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar doador", description = "Método responsável por atualizar os dados de um doador.")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody DoadorRequest doador){
        try {
            DoadorResponse doadorBanco = doadorService.salvarDoador(doador,id);

            if(doadorBanco == null){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(doadorBanco);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Endpoint de exclusão: localiza o registro e aplica a lógica de exclusão definida pelo projeto antes de salvar a
    //alteração.
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Excluir doador", description = "Método responsável por excluir um doador existente do sistema.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        var atualizarDoador = doadorService.atualizarStatus(EnumStatusDoador.EXCLUIDO,id);

        if(atualizarDoador){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

}
