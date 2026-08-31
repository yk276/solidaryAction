package com.example.solidaryaction.repository;

import com.example.solidaryaction.entities.Campanha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha,Long> {
}
