package com.example.solidaryaction.presentation;

//Controller REST de Doacao. Recebe requisições HTTP, chama o Repository correspondente, monta
//respostas HTTP e documenta os endpoints para o Swagger.

import com.example.solidaryaction.application.DTOs.AtualizarStatusRequestDoacao;
import com.example.solidaryaction.domain.entities.Doacao;
import com.example.solidaryaction.domain.entities.EnumStatusDoacao;
import com.example.solidaryaction.domain.repository.DoacaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Este controller recebe requisições HTTP desta entidade. @RequestMapping define a rota base e o Swagger usa
//@Tag/@Operation para documentar os endpoints.
@RestController
@RequestMapping("/doacoes")
@Tag(name = "Doações", description = "Controller responsável pela gestão das doações do sistema.")
public class DoacaoController {

    @Autowired
    private DoacaoRepository doacaoRepository;

    // Endpoint GET: consulta dados. O método usa o Repository para buscar no banco e devolve a resposta HTTP ao
    //frontend/Swagger.
    @GetMapping
    @Operation(summary = "Listar doações", description = "Método responsável por consultar todas as doações registradas.")
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(doacaoRepository.findAll());
    }

    //Endpoint POST: recebe um objeto no corpo da requisição e salva um novo registro usando o Repository.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar doação", description = "Método responsável por criar uma nova doação na plataforma.")
    public ResponseEntity<Doacao> criar(@RequestBody Doacao doacao){

        var doacaoBanco = doacaoRepository.save(doacao);
        return ResponseEntity.ok(doacaoBanco);

    }

    //Endpoint GET por ID: recebe o identificador pela URL, procura o registro no Repository e retorna 200 quando
    //encontra ou 404 quando não encontra.
    @GetMapping("/{id}")
    @Operation(summary = "Buscar doação por ID", description = "Método responsável por consultar uma doação específica.")
    public ResponseEntity<Doacao> buscarPorId(@PathVariable Long id){

        Doacao doacaoBanco = doacaoRepository.findById(id).orElse(null);
        if(doacaoBanco != null){
            return ResponseEntity.ok(doacaoBanco);
        }
        return ResponseEntity.notFound().build();
    }

    //Endpoint PATCH: altera somente o status do registro, sem substituir todos os demais campos.
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status da doação", description = "Método responsável por alterar o status de uma doação.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequestDoacao statusRequestDoacao){

        Doacao doacaoBanco = doacaoRepository.findById(id).orElse(null);
        if(doacaoBanco != null ){
            doacaoBanco.setStatus(statusRequestDoacao.status());
            doacaoRepository.save(doacaoBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Endpoint PUT: busca o registro existente, atualiza seus campos e salva novamente no banco.
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar doação", description = "Método responsável por atualizar os dados de uma doação.")
    public ResponseEntity<Doacao> atualizar(@PathVariable Long id, @RequestBody Doacao doacao){
        try{
            Doacao doacaoBanco = doacaoRepository.findById(id).orElse(null);
            if(doacaoBanco != null) {
                doacaoBanco.setDataDoacao(doacao.getDataDoacao());
                doacaoBanco.setValorDoado(doacao.getValorDoado());
                doacaoBanco.setDescricao(doacao.getDescricao());
                doacaoRepository.save(doacaoBanco);
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
    @Operation(summary = "Excluir doação", description = "Método responsável por excluir uma doação existente do sistema.")
    public ResponseEntity<Void> excluir(@PathVariable Long id){

        Doacao doacaoBanco = doacaoRepository.findById(id).orElse(null);
        if(doacaoBanco != null ){
            doacaoBanco.setStatus(EnumStatusDoacao.EXCLUIDO);
            doacaoRepository.save(doacaoBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

}
