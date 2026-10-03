package com.example.solidaryaction.domain.entities;

//Entidade JPA `Doacao`. Representa a estrutura persistida no banco e usa Lombok para gerar
//automaticamente métodos e construtores, reduzindo código repetitivo.

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

//As anotações JPA transformam esta classe em uma entidade persistida no banco. O Lombok reduz código repetitivo,
//como getters, setters e construtores.
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doacao {

    // Este campo é a chave primária da entidade; @GeneratedValue deixa a geração do identificador a cargo do
    //banco/JPA.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String dataDoacao;

    public BigDecimal valorDoado;

    public String descricao;

    public EnumStatusDoacao status = EnumStatusDoacao.PENDENTE;
}
