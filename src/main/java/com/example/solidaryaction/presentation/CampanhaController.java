package com.example.solidaryaction.presentation;

//Controller REST de Campanha. Recebe requisições HTTP, chama o Repository correspondente, monta
//respostas HTTP e documenta os endpoints para o Swagger.

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.application.services.CampanhaService;
import com.example.solidaryaction.domain.repository.CampanhaRepository;
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
@RequestMapping("/campanhas")
@Tag(name = "Campanhas", description = "Controller responsável pela gestão das campanhas do sistema.")
public class CampanhaController {

    @Autowired
    private CampanhaRepository campanhaRepository;

    @Autowired
    private CampanhaService campanhaService;

    //Endpoint GET: consulta dados. O método usa o Repository para buscar no banco e devolve a resposta HTTP ao
    //frontend/Swagger.
    @GetMapping
    @Operation(summary = "Listar campanhas", description = "Método responsável por consultar todas as campanhas cadastradas.")
    public ResponseEntity<List<CampanhaResponse>> listarTodos(){

        return  ResponseEntity.ok(campanhaService.listarTodasCampanhasTable());
    }


    //Endpoint POST: recebe um objeto no corpo da requisição e salva um novo registro usando o Repository.
    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar campanha", description = "Método responsável por registrar uma nova campanha no sistema.")
    public ResponseEntity<?> criar(@RequestBody CampanhaRequest campanha){

        try {
            CampanhaResponse campanhaBanco = campanhaService.salvarCampanha(campanha,0L);
            return ResponseEntity.ok(campanhaBanco);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    //Endpoint GET por ID: recebe o identificador pela URL, procura o registro no Repository e retorna 200 quando
    //encontra ou 404 quando não encontra.
    @GetMapping("/{id}")
    @Operation(summary = "Buscar campanha por ID", description = "Método responsável por consultar uma campanha específica.")
    public ResponseEntity<CampanhaResponse> buscarPorId(@PathVariable Long id){

        CampanhaResponse campanhaBanco = campanhaService.buscarPorId(id);
        if(campanhaBanco != null){
            return ResponseEntity.ok(campanhaBanco);
        }
        return ResponseEntity.notFound().build();
    }

    //Endpoint PATCH: altera somente o status do registro, sem substituir todos os demais campos.
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status da campanha", description = "Método responsável por alterar o status de uma campanha.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequestCampanha statusRequestCampanha){

        var atualizarCampanha = campanhaService.atualizarStatus(statusRequestCampanha.status(),id);

        if(atualizarCampanha){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Endpoint PUT: busca o registro existente, atualiza seus campos e salva novamente no banco.
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar campanha", description = "Método responsável por atualizar os dados de uma campanha.")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody CampanhaRequest campanha){
        try {
            CampanhaResponse campanhaBanco = campanhaService.salvarCampanha(campanha,id);

            if(campanhaBanco == null){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(campanhaBanco);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Endpoint de exclusão: localiza o registro e aplica a lógica de exclusão definida pelo projeto antes de salvar a
    //alteração.
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Excluir campanha", description = "Método responsável por excluir uma campanha existente do sistema.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        var atualizarCampanha = campanhaService.atualizarStatus(EnumStatusCampanha.EXCLUIDO,id);

        if(atualizarCampanha){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

}
