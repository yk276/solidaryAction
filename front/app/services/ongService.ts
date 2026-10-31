import { Ong } from "../types/ong";
import api from "./api";

export async function buscarListaOngs(): Promise<Ong[]> {

    const dados = await api.get<Ong[]>(
        "/ongs"
    );

    if (dados.status == 200) {
        return dados.data;
    }

    return [];
}

export async function deletarOng(id: number): Promise<number> {

    var dados = await api.delete(
        '/ongs/' + id + '/excluir'
    );

    return dados.status;
}

export async function atualizarStatusOng(ong: Ong): Promise<void> {

    var novoStatus = {};

    if (ong.status === "ATIVO") {
        novoStatus = {status: "BLOQUEADO"};
    } else {
        novoStatus = {status: "ATIVO"};
    }

    var dadosRetorno = await api.patch(
        '/ongs/' + ong.id + '/status',
        novoStatus
    );

    if (dadosRetorno.status == 200) {
        alert("Atualizado status com sucesso!");
    } else {
        alert(dadosRetorno.data);
        return;
    }
}

export async function consultaOngPorId(id: number): Promise<Ong> {

    const valorOngBack = await api.get<Ong>(
        '/ongs/' + id
    );

    return valorOngBack.data;
}

export async function atualizarOng(ong: Ong): Promise<Ong> {

    var dadosRetorno = await api.put<Ong>(
        '/ongs/' + ong.id,
        ong
    );

    return dadosRetorno.data;
}

export async function salvarOng(ong: Ong): Promise<Ong> {

    var dadosRetorno = await api.post<Ong>(
        '/ongs/criar',
        ong
    );

    return dadosRetorno.data;
}