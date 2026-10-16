package com.example.solidaryaction.application.DTOs;

//Enumeração que limita os valores possíveis de status de `Doador`. Evita strings livres no
//backend e deixa os estados permitidos explícitos no código.

//Este enum limita o status aos valores previstos pelo sistema, evitando textos livres e inconsistentes no código.
public enum EnumStatusDoador {
    ATIVO,
    BLOQUEADO,
    EXCLUIDO
}
