package com.example.solidaryaction.domain.repository;

//Repository de `Ong`. É a camada de acesso aos dados; ao estender `JpaRepository`, recebe
//operações prontas como `findAll`, `findById`, `save` e `delete`.

import com.example.solidaryaction.domain.entities.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//O Repository é a camada de acesso a dados. Ao estender JpaRepository, recebe operações prontas como save,
//findAll e findById sem precisar escrever SQL básico.
public interface OngRepository extends JpaRepository<Ong,Long> {
}
