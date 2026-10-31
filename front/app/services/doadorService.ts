import { Doador } from "../types/doador";
import api from "./api";

export async function buscarListaDoadores(): Promise<Doador[]> {

    const dados = await api.get<Doador[]>(
        "/doadores"
    );

    if (dados.status == 200) {
        return dados.data;
    }

    return [];
}

export async function deletarDoador(id: number): Promise<number> {

    var dados = await api.delete(
        '/doadores/' + id + '/excluir'
    );

    return dados.status;
}

export async function atualizarStatusDoador(
    doador: Doador
): Promise<void> {

    var novoStatus = {};

    if (doador.status === "ATIVO") {
        novoStatus = {status: "BLOQUEADO"};
    } else {
        novoStatus = {status: "ATIVO"};
    }

    var dadosRetorno = await api.patch(
        '/doadores/' + doador.id + '/status',
        novoStatus
    );

    if (dadosRetorno.status == 200) {
        alert("Status atualizado com sucesso!");
    } else {
        alert(dadosRetorno.data);
        return;
    }
}

export async function consultaDoadorPorId(
    id: number
): Promise<Doador> {

    const valorDoadorBack = await api.get<Doador>(
        '/doadores/' + id
    );

    return valorDoadorBack.data;
}

export async function atualizarDoador(
    doador: Doador
): Promise<number> {

    var dadosRetorno = await api.put<number>(
        '/doadores/' + doador.id,
        doador
    );

    return dadosRetorno.data;
}

export async function salvarDoador(
    doador: Doador
): Promise<number> {

    var dadosRetorno = await api.post<number>(
        '/doadores/criar',
        doador
    );

    return dadosRetorno.data;
}