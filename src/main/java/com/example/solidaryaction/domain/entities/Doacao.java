package com.example.solidaryaction.domain.entities;

import com.example.solidaryaction.application.DTOs.DoacaoRequest;
import com.example.solidaryaction.application.DTOs.EnumStatusDoacao;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String dataDoacao;

    private BigDecimal valorDoado;

    public String descricao;

    public EnumStatusDoacao status = EnumStatusDoacao.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "campanha_id")
    private Campa campanha;

    @ManyToOne
    @JoinColumn(name = "doador_id")
    private Doador doador;

    public void setValorDoado(BigDecimal valorDoado) {
        if (valorDoado == null || valorDoado.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O valor da doação deve ser maior que zero!"
            );
        }

        this.valorDoado = valorDoado;
    }

    public Doacao(DoacaoRequest doacao) {
        this.setDataDoacao(doacao.dataDoacao());
        this.setValorDoado(doacao.valorDoado());
        this.setDescricao(doacao.descricao());
    }
}