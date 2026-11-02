package com.example.solidaryaction.domain.entities;

import com.example.solidaryaction.application.DTOs.DoadorRequest;
import com.example.solidaryaction.application.DTOs.EnumStatusDoador;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doador {

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