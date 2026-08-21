package com.example.solidaryaction.repository;

import com.example.solidaryaction.entities.Doacao;
import com.example.solidaryaction.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao,Long> {
}
