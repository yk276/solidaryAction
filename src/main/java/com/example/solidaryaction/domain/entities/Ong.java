package com.example.solidaryaction.domain.entities;

//Entidade JPA `Ong`. Representa a estrutura persistida no banco e usa Lombok para gerar
//automaticamente métodos e construtores, reduzindo código repetitivo.

import com.example.solidaryaction.application.DTOs.CriarOngAdminRequest;
import com.example.solidaryaction.application.DTOs.EnumStatusOng;
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
public class Ong {

    //Este campo é a chave primária da entidade; @GeneratedValue deixa a geração do identificador a cargo do
    //banco/JPA.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String nomeFantasia;

    public String razaoSocial;

    public String cnpj;

    public String email;

    public String endereco;

    public BigDecimal totalArrecadado;

    private String role = "ROLE_ONG";

    public EnumStatusOng status = EnumStatusOng.ATIVO;

    public Ong(CriarOngAdminRequest criarOngAdminRequest) {
        this.setNomeFantasia(criarOngAdminRequest.nomeFantasia());
        this.setRazaoSocial(criarOngAdminRequest.razaoSocial());
        this.setCnpj(criarOngAdminRequest.cnpj());
        this.setEmail(criarOngAdminRequest.email());
        this.setEndereco(criarOngAdminRequest.endereco());
        this.setTotalArrecadado(criarOngAdminRequest.totalArrecadado());
        this.setRole("ROLE_ADMIN");
    }
}
