package com.example.solidaryaction.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campanha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String titulo;

    public String descricao;

    public BigDecimal metaFinanceira;

    public BigDecimal totalArrecadado;

    public LocalDate dataInicio;

    public LocalDate dataFim;

    public EnumStatusCampanha status = EnumStatusCampanha.RASCUNHO;
}
