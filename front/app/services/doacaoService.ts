import axios from "axios";
import { Doacao } from "../types/doacao";
import api from "./api";

export async function buscarListaDoacoes(): Promise<Doacao[]> {

    const dados = await api.get<Doacao[]>(
        "http://localhost:8080/doacoes"
    );

    if (dados.status == 200) {
        return dados.data;
    }

    return [];
}

export async function deletarDoacao(id: number): Promise<number> {

    var dados = await api.delete(
        'http://localhost:8080/doacoes/' + id + '/excluir'
    );

    return dados.status;
}

export async function atualizarStatusDoacao(
    doacao: Doacao
): Promise<void> {

    if (doacao.status === "EXCLUIDO") {
        return;
    }

    var novoStatus;

    if (doacao.status === "CONFIRMADO") {
        novoStatus = {status: "CANCELADO"};
    } else {
        novoStatus = {status: "CONFIRMADO"};
    }

    try {
        const dadosRetorno = await api.patch<Doacao>(
            'http://localhost:8080/doacoes/' + doacao.id + '/status',
            novoStatus
        );

        alert("Status atualizado com sucesso!");

        if (
            dadosRetorno.data.status === "CONFIRMADO" &&
            dadosRetorno.data.metaAtingida
        ) {
            alert(dadosRetorno.data.mensagem);
        }

    } catch (erro) {
        if (axios.isAxiosError(erro)) {

            if (erro.response?.status === 403) {
                alert("O servidor recusou a alteração (403). Confira o console do backend.");

            } else if (erro.response?.status === 401) {
                alert("Autenticação inválida. Faça login novamente.");

            } else if (erro.response?.status === 404) {
                alert("Doação não encontrada no acesso permitido.");

            } else if (typeof erro.response?.data === "string") {
                alert(erro.response.data);

            } else {
                alert("Não foi possível alterar o status da doação.");
            }

        } else {
            alert("Não foi possível alterar o status da doação.");
        }
    }
}

export async function consultaDoacaoPorId(
    id: number
): Promise<Doacao> {

    const valorDoacaoBack = await api.get<Doacao>(
        'http://localhost:8080/doacoes/' + id
    );

    return valorDoacaoBack.data;
}

export async function atualizarDoacao(
    doacao: Doacao
): Promise<Doacao> {

    const dados = {
        dataDoacao: doacao.dataDoacao,
        valorDoado: doacao.valorDoado,
        descricao: doacao.descricao,
        campanhaId: doacao.campanhaId,
        doadorId: doacao.doadorId
    };

    var dadosRetorno = await api.put<Doacao>(
        'http://localhost:8080/doacoes/' + doacao.id,
        dados
    );

    return dadosRetorno.data;
}

export async function salvarDoacao(
    doacao: Doacao
): Promise<Doacao> {

    const dados = {
        dataDoacao: doacao.dataDoacao,
        valorDoado: doacao.valorDoado,
        descricao: doacao.descricao,
        campanhaId: doacao.campanhaId,
        doadorId: doacao.doadorId
    };

    var dadosRetorno = await api.post<Doacao>(
        'http://localhost:8080/doacoes/criar',
        dados
    );

    return dadosRetorno.data;
}

//Preserva o tratamento de erros usado no formulário de doação.
export function tratarErroSalvarDoacao(erro: unknown): void {

    if (axios.isAxiosError(erro)) {

        if (erro.response?.status === 404) {
            alert("Campanha, doador ou doação não encontrados no acesso permitido.");

        } else if (
            erro.response?.status === 400 &&
            typeof erro.response.data === "string"
        ) {
            alert(erro.response.data);

        } else {
            alert("Não foi possível salvar a doação. Verifique os dados e tente novamente.");
        }

    } else {
        alert("Não foi possível salvar a doação.");
    }
}