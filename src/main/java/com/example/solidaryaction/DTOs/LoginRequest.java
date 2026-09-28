package com.example.solidaryaction.DTOs;
//Transporta apenas os dados necessários para uma requisição/resposta
//específica, evitando usar uma entidade completa quando não é preciso.

    public record LoginRequest(String email, String senha){

    }

