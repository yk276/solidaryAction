package com.example.solidaryaction.domain.entities;

//Entidade JPA `Doador`. Representa a estrutura persistida no banco e usa Lombok para gerar
//automaticamente métodos e construtores, reduzindo código repetitivo.

import com.example.solidaryaction.application.DTOs.DoadorRequest;
import com.example.solidaryaction.application.DTOs.EnumStatusDoador;
import com.example.solidaryaction.application.DTOs.OngRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//As anotações JPA transformam esta classe em uma entidade persistida no banco. O Lombok reduz código repetitivo,
//como getters, setters e construtores.
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doador {

    //Este campo é a chave primária da entidade; @GeneratedValue deixa a geração do identificador a cargo do
    //banco/JPA.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;

    private String email;

    private String profissao;

    private String endereco;

    private EnumStatusDoador status = EnumStatusDoador.ATIVO;

    @ManyToOne
    @JoinColumn(name = "ong_id", referencedColumnName = "id")
    private Ong ong;

    @OneToMany(mappedBy = "doador")
    private List<Doacao> doacao;

    public Doador(DoadorRequest doador) {
        this.setNome(doador.nome());
        this.setCpf(doador.cpf());
        this.setEmail(doador.email());
        this.setProfissao(doador.profissao());
        this.setEndereco(doador.endereco());
    }
}
