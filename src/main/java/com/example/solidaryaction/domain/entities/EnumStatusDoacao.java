package com.example.solidaryaction.domain.entities;

//Enumeração que limita os valores possíveis de status de `Doacao`. Evita strings livres no
//backend e deixa os estados permitidos explícitos no código.

//Este enum limita o status aos valores previstos pelo sistema, evitando textos livres e inconsistentes no código.
public enum EnumStatusDoacao {
    PENDENTE,
    CONFIRMADO,
    CANCELADO,
    EXCLUIDO

}
