package com.example.solidaryaction.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

//Entidade JPA `Campanha`. Representa a estrutura persistida no banco e usa Lombok para gerar
//automaticamente métodos e construtores, reduzindo código repetitivo.

//As anotações JPA transformam esta classe em uma entidade persistida no banco. O Lombok reduz código repetitivo,
//como getters, setters e construtores.
@Entity
@Data //gera getters, setters e métodos utilitários;
@NoArgsConstructor //permite criar o objeto vazio; (importante para o JPA)
@AllArgsConstructor //permite criar o objeto já preenchendo todos os atributos.
public class Campanha {

    //Este campo é a chave primária da entidade; @GeneratedValue deixa a geração do identificador a cargo do
    //banco/JPA.
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
