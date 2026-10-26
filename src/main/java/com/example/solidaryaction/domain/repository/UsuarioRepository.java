package com.example.solidaryaction.domain.repository;

//Repository de `Usuario`. É a camada de acesso aos dados; ao estender `JpaRepository`, recebe
//operações prontas como `findAll`, `findById`, `save` e `delete`.

import com.example.solidaryaction.application.DTOs.EnumStatusUsuario;
import com.example.solidaryaction.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//O Repository é a camada de acesso a dados. Ao estender JpaRepository, recebe operações prontas como save,
//findAll e findById sem precisar escrever SQL básico.
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    boolean existsUsuarioByEmailAndSenha(String email, String senha);

    Optional<Usuario> findByEmailAndSenha(String email, String senha);

    //Este método segue a convenção de nomes do Spring Data JPA; o framework monta automaticamente a consulta a partir
    //do nome do método.
    Optional<List<Usuario>> findByStatusNot(EnumStatusUsuario status);

    List<Usuario> findByOng_Id(Long ongId);

    Optional<Usuario> findByIdAndOng_Id(Long id, Long ongId);

}
