package com.example.solidaryaction.repository;

import com.example.solidaryaction.entities.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OngRepository extends JpaRepository<Ong,Long> {
}
