package com.example.solidaryaction.domain.entities;

import com.example.solidaryaction.application.DTOs.CampanhaRequest;
import com.example.solidaryaction.application.DTOs.EnumStatusCampanha;
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
public class Campanha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String titulo;

    public String descricao;

    public BigDecimal metaFinanceira;

    public BigDecimal totalArrecadado = BigDecimal.ZERO;

    public String dataInicio;

    public String dataFim;

    public EnumStatusCampanha status = EnumStatusCampanha.RASCUNHO;

    @ManyToOne
    @JoinColumn(name = "ong_id")
    private Ong ong;

    public boolean isMetaAtingida() {

        if (metaFinanceira == null || totalArrecadado == null) {
            return false;
        }

        return totalArrecadado.compareTo(metaFinanceira) >= 0;
    }

    public Campanha(CampanhaRequest campanha) {
        this.setTitulo(campanha.titulo());
        this.setDescricao(campanha.descricao());
        this.setMetaFinanceira(campanha.metaFinanceira());
        this.setDataInicio(campanha.dataInicio());
        this.setDataFim(campanha.dataFim());
    }
}