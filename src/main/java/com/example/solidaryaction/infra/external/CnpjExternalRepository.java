package com.example.solidaryaction.infra.external;

import com.example.solidaryaction.domain.interfaces.ICnpjExternalRepository;
import com.example.solidaryaction.infra.dto.BrasilApiCnpjResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Locale;
import java.util.NoSuchElementException;

@Component
public class CnpjExternalRepository implements ICnpjExternalRepository {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public BrasilApiCnpjResponse buscarCnpj(String cnpj) {

        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o CNPJ!");
        }

        //Remove a formatação e mantém números e letras.
        String cnpjConsulta = cnpj
                .replaceAll("[^a-zA-Z0-9]", "")
                .toUpperCase(Locale.ROOT);

        if (cnpjConsulta.length() != 14) {
            throw new IllegalArgumentException(
                    "O CNPJ deve possuir 14 caracteres!"
            );
        }

        String url = "https://brasilapi.com.br/api/cnpj/v1/"
                + cnpjConsulta;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 200) {

                BrasilApiCnpjResponse dados = objectMapper.readValue(
                        response.body(),
                        BrasilApiCnpjResponse.class
                );

                if (dados.getCnpj() == null
                        || dados.getCnpj().trim().isEmpty()) {

                    throw new IllegalStateException(
                            "A API não retornou o CNPJ!"
                    );
                }

                return dados;
            }

            if (response.statusCode() == 400) {
                throw new IllegalArgumentException(
                        "CNPJ inválido ou mal formatado!"
                );
            }

            if (response.statusCode() == 404) {
                throw new NoSuchElementException(
                        "CNPJ não encontrado na consulta externa!"
                );
            }

            throw new IllegalStateException(
                    "Não foi possível consultar a API de CNPJ!"
            );

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Erro na comunicação com a API de CNPJ!",
                    e
            );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new IllegalStateException(
                    "A consulta de CNPJ foi interrompida!",
                    e
            );
        }
    }
}