package com.example.solidaryaction.domain.entities;

//Entidade JPA `Ong`. Representa a estrutura persistida no banco e usa Lombok para gerar
//automaticamente métodos e construtores, reduzindo código repetitivo.

import com.example.solidaryaction.application.DTOs.CriarOngAdminRequest;
import com.example.solidaryaction.application.DTOs.EnumStatusOng;
import com.example.solidaryaction.application.DTOs.OngRequest;
import com.example.solidaryaction.domain.valueobject.CNPJ;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

//As anotações JPA transformam esta classe em uma entidade persistida no banco. O Lombok reduz código repetitivo,
//como getters, setters e construtores.
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ong {

    //Este campo é a chave primária da entidade; @GeneratedValue deixa a geração do identificador a cargo do
    //banco/JPA.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeFantasia;

    private String razaoSocial;

    @Embedded
    private CNPJ cnpj;

    private String email;

    private String endereco;

    private BigDecimal totalArrecadado = BigDecimal.ZERO;

    private EnumStatusOng status = EnumStatusOng.ATIVO;

    @OneToMany(mappedBy = "ong")
    private List<Doador> doadores;

    public Ong(CriarOngAdminRequest criarOngAdminRequest) {
        this.setNomeFantasia(criarOngAdminRequest.nomeFantasia());
        this.setRazaoSocial(criarOngAdminRequest.razaoSocial());
        this.setCnpj(new CNPJ(criarOngAdminRequest.cnpj()));
        this.setEmail(criarOngAdminRequest.email());
        this.setEndereco(criarOngAdminRequest.endereco());
    }

    public Ong(OngRequest ong) {
        this.setNomeFantasia(ong.nomeFantasia());
        this.setRazaoSocial(ong.razaoSocial());
        this.setCnpj(new CNPJ(ong.cnpj()));
        this.setEmail(ong.email());
        this.setEndereco(ong.endereco());
    }
}
