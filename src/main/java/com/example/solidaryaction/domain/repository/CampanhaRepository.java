package com.example.solidaryaction.domain.repository;
//principio liskov

//Repository de `Campanha`. É a camada de acesso aos dados; ao estender `JpaRepository`, recebe
//operações prontas como `findAll`, `findById`, `save` e `delete`.

import com.example.solidaryaction.domain.entities.Campanha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
//O Repository é a camada de acesso a dados. Ao estender JpaRepository, recebe operações prontas como save,
//findAll e findById sem precisar escrever SQL básico.
public interface CampanhaRepository extends JpaRepository<Campanha,Long> {

    List<Campanha> findByOng_Id(Long ongId);

    Optional<Campanha> findByIdAndOng_Id(Long id, Long ongId);
}
//presentation (controller) -> controller -> mostrar os dados
//controller controla a apllication (services, dtos) -> casos de uso
//domain (repository, entidade) -> validação
// if caso de uso