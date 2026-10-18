package com.example.solidaryaction.presentation;

//Controller REST de Ong. Recebe requisições HTTP, chama o Repository correspondente, monta
//respostas HTTP e documenta os endpoints para o Swagger.

import com.example.solidaryaction.application.DTOs.*;
import com.example.solidaryaction.application.services.OngService;
import com.example.solidaryaction.application.DTOs.EnumStatusOng;
import com.example.solidaryaction.domain.entities.Ong;
import com.example.solidaryaction.domain.repository.OngRepository;
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
@RequestMapping("/ongs")
@Tag(name = "ONGs", description = "Controller responsável pela gestão das ONGs do sistema.")
public class OngController {

    @Autowired
    private OngRepository ongRepository;

    @Autowired
    private OngService ongService;

    // Endpoint GET: consulta dados. O método usa o Repository para buscar no banco e devolve a resposta HTTP ao
    //frontend/Swagger.
    @GetMapping
    @Operation(summary = "Listar ONGs", description = "Método responsável por consultar todas as ONGs cadastradas.")
    public ResponseEntity<List<OngResponse>> listarTodos(){

        return  ResponseEntity.ok(ongService.listarTodasOngsTable());
    }

    //Endpoint POST: recebe um objeto no corpo da requisição e salva um novo registro usando o Repository.
    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar ONG", description = "Método responsável por registrar uma nova ONG no sistema.")
    public ResponseEntity<?> criar(@RequestBody OngRequest ong){

        try {
            OngResponse ongBanco = ongService.salvarOng(ong,0L);
            return ResponseEntity.ok(ongBanco);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/admin")
    @Operation(summary = "Criar ONG admin", description = "Método responsável por registrar uma nova ONG admin no sistema.")
    public ResponseEntity<CriarOngAdminResponse> criarOngAdmin(@RequestBody CriarOngAdminRequest criarOngAdminRequest){

        try{
            CriarOngAdminResponse respostaSalvar = ongService.criarOngAdmin(criarOngAdminRequest);
            return ResponseEntity.ok(respostaSalvar);

        } catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }

    }

    //Endpoint GET por ID: recebe o identificador pela URL, procura o registro no Repository e retorna 200 quando
    //encontra ou 404 quando não encontra.
    @GetMapping("/{id}")
    @Operation(summary = "Buscar ONG por ID", description = "Método responsável por consultar uma ONG específica")
    public ResponseEntity<OngResponse> buscarPorId(@PathVariable Long id){

        OngResponse ongBanco = ongService.buscarPorId(id);
        if (ongBanco != null) {
            return ResponseEntity.ok(ongBanco);
        }
        return ResponseEntity.notFound().build();
    }

    //Endpoint PATCH: altera somente o status do registro, sem substituir todos os demais campos.
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status da ONG", description = "Método responsável por alterar o status de uma ONG.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequestOng statusRequestOng){

        var atualizarOng = ongService.atualizarStatus(statusRequestOng.status(),id);

        if(atualizarOng){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Endpoint PUT: busca o registro existente, atualiza seus campos e salva novamente no banco.
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ONG", description = "Método responsável por atualizar os dados de uma ONG.")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody OngRequest ong){
        try {
            OngResponse ongBanco = ongService.salvarOng(ong,id);

            if(ongBanco == null){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(ongBanco);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Endpoint de exclusão: localiza o registro e aplica a lógica de exclusão definida pelo projeto antes de salvar a
    //alteração.
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Excluir ONG", description = "Método responsável por excluir uma ONG existente do sistema.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        var atualizarOng = ongService.atualizarStatus(EnumStatusOng.EXCLUIDO,id);

        if(atualizarOng){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

}
