package com.example.solidaryaction.domain.valueobject;

import jakarta.persistence.Embeddable;
// value object = DDD
@Embeddable
public class CNPJ {

    private String cnpj;

    public CNPJ() {
        this.cnpj = "";
    }

    public CNPJ(String cnpj) {
        if (!isValidCnpj(cnpj)) {
            throw new IllegalArgumentException("CNPJ inválido!");
        }

        this.cnpj = cnpj.replaceAll("[./-]", "");
    }

    public static boolean isValidCnpj(String cnpj) {
        if (cnpj == null) {
            return false;
        }

        // Remove a pontuação.
        cnpj = cnpj.replaceAll("[./-]", "");

        // Verifica os 14 números e rejeita sequências iguais.
        if (!cnpj.matches("[0-9]{14}")) {
            return false;
        }

        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Calcula o primeiro e o segundo dígitos verificadores.
        for (int tamanho = 12; tamanho <= 13; tamanho++) {
            int soma = 0;
            int peso = 2;

            for (int i = tamanho - 1; i >= 0; i--) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso;
                peso++;

                if (peso > 9) {
                    peso = 2;
                }
            }

            int resto = soma % 11;
            int digito = 11 - resto;

            if (resto < 2) {
                digito = 0;
            }

            if (digito != Character.getNumericValue(cnpj.charAt(tamanho))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return cnpj.replaceAll(
                "(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})",
                "$1.$2.$3/$4-$5"
        );
    }
}