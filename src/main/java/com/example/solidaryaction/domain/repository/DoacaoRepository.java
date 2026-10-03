package com.example.solidaryaction.domain.repository;

//Repository de `Doacao`. É a camada de acesso aos dados; ao estender `JpaRepository`, recebe
//operações prontas como `findAll`, `findById`, `save` e `delete`.

import com.example.solidaryaction.domain.entities.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//O Repository é a camada de acesso a dados. Ao estender JpaRepository, recebe operações prontas como save,
//findAll e findById sem precisar escrever SQL básico.
public interface DoacaoRepository extends JpaRepository<Doacao,Long> {
}
