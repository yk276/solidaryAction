package com.example.solidaryaction.presentation;

//Controller REST de Doador. Recebe requisições HTTP, chama o Repository correspondente, monta
//respostas HTTP e documenta os endpoints para o Swagger.

import com.example.solidaryaction.application.DTOs.AtualizarStatusRequestDoador;
import com.example.solidaryaction.application.DTOs.DoadorResponse;
import com.example.solidaryaction.application.services.DoadorService;
import com.example.solidaryaction.domain.entities.Doador;
import com.example.solidaryaction.domain.entities.EnumStatusDoador;
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

        return  ResponseEntity.ok(doadorService.listarTodasDoadoresTable());
    }

    //Endpoint POST: recebe um objeto no corpo da requisição e salva um novo registro usando o Repository.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar doador", description = "Método responsável por registrar um novo doador no sistema.")
    public ResponseEntity<Doador> criar(@RequestBody Doador doador){

        var doadorBanco = doadorRepository.save(doador);
        return ResponseEntity.ok(doadorBanco);

    }

    //Endpoint GET por ID: recebe o identificador pela URL, procura o registro no Repository e retorna 200 quando
    //encontra ou 404 quando não encontra.
    @GetMapping("/{id}")
    @Operation(summary = "Buscar doador por ID", description = "Método responsável por consultar um doador específico.")
    public ResponseEntity<Doador> buscarPorId(@PathVariable Long id){

        Doador doadorBanco = doadorRepository.findById(id).orElse(null);
        if(doadorBanco != null){
            return ResponseEntity.ok(doadorBanco);
        }
        return ResponseEntity.notFound().build();
    }

    //Endpoint PATCH: altera somente o status do registro, sem substituir todos os demais campos.
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do doador", description = "Método responsável por alterar o status de um doador.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequestDoador statusRequestDoador){

        Doador doadorBanco = doadorRepository.findById(id).orElse(null);
        if(doadorBanco != null ){
            doadorBanco.setStatus(statusRequestDoador.status());
            doadorRepository.save(doadorBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Endpoint PUT: busca o registro existente, atualiza seus campos e salva novamente no banco.
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar doador", description = "Método responsável por atualizar os dados de um doador.")
    public ResponseEntity<Doador> atualizar(@PathVariable Long id, @RequestBody Doador doador){
        try{
            Doador doadorBanco = doadorRepository.findById(id).orElse(null);
            if(doadorBanco != null) {
                doadorBanco.setNome(doador.getNome());
                doadorBanco.setCpf(doador.getCpf());
                doadorBanco.setEmail(doador.getEmail());
                doadorBanco.setEndereco(doador.getEndereco());
                doadorBanco.setProfissao(doador.getProfissao());
                doadorRepository.save(doadorBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    //Endpoint de exclusão: localiza o registro e aplica a lógica de exclusão definida pelo projeto antes de salvar a
    //alteração.
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Excluir doador", description = "Método responsável por excluir um doador existente do sistema.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Doador doadorBanco = doadorRepository.findById(id).orElse(null);
        if(doadorBanco != null ){
            doadorBanco.setStatus(EnumStatusDoador.EXCLUIDO);
            doadorRepository.save(doadorBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

}
