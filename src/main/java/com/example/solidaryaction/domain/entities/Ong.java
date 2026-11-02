package com.example.solidaryaction.domain.entities;

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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ong {

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