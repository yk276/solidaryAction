import { Campanha } from "../types/campanha";
import api from "./api";

export async function buscarListaCampanhas(): Promise<Campanha[]> {

    const dados = await api.get<Campanha[]>(
        "http://localhost:8080/campanhas"
    );

    if (dados.status == 200) {
        return dados.data;
    }

    return [];
}

export async function deletarCampanha(id: number): Promise<number> {

    var dados = await api.delete(
        'http://localhost:8080/campanhas/' + id + '/excluir'
    );

    return dados.status;
}

export async function atualizarStatusCampanha(
    campanha: Campanha
): Promise<void> {

    var novoStatus = {};

    if (campanha.status === "ATIVO") {
        novoStatus = {status: "BLOQUEADO"};
    } else {
        novoStatus = {status: "ATIVO"};
    }

    var dadosRetorno = await api.patch(
        'http://localhost:8080/campanhas/' + campanha.id + '/status',
        novoStatus
    );

    if (dadosRetorno.status == 200) {
        alert("Status atualizado com sucesso!");
    } else {
        alert(dadosRetorno.data);
        return;
    }
}

export async function consultaCampanhaPorId(
    id: number
): Promise<Campanha> {

    const valorCampanhaBack = await api.get<Campanha>(
        'http://localhost:8080/campanhas/' + id
    );

    return valorCampanhaBack.data;
}

export async function atualizarCampanha(
    campanha: Campanha
): Promise<number> {

    var dadosRetorno = await api.put<number>(
        'http://localhost:8080/campanhas/' + campanha.id,
        campanha
    );

    return dadosRetorno.data;
}

export async function salvarCampanha(
    campanha: Campanha
): Promise<number> {

    var dadosRetorno = await api.post<number>(
        'http://localhost:8080/campanhas/criar',
        campanha
    );

    return dadosRetorno.data;
}