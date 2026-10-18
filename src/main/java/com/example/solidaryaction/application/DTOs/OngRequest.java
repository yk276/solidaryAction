package com.example.solidaryaction.application.DTOs;

import java.math.BigDecimal;

public record OngRequest(String nomeFantasia, String razaoSocial, String cnpj, String email, String endereco, BigDecimal totalArrecadado) {
}
