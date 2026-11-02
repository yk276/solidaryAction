'use client'

import {
    atualizarStatusCampanha,
    buscarListaCampanhas,
    deletarCampanha
} from "@/app/services/campanhaService";
import { Campanha } from "@/app/types/campanha";
import { RootState } from "../../redux/store";
import { alternarFavorito } from "../../redux/slices/favoritosSlice";
import Link from "next/link";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";

//Tela de listagem e gestão de campanhas.
export default function Campanhas(){

    const [campanhas, setCampanhas] = useState<Campanha[]>([]);

    const dispatch = useDispatch();

    const usuarioLogado = useSelector(
        (state: RootState) => state.auth.usuario
    );

    const favoritos = useSelector(
        (state: RootState) => state.favoritos.campanhasIds
    );

    const podeCriar = usuarioLogado?.role === "ROLE_COORDENADOR";

    const podeAlterar = usuarioLogado?.role === "ROLE_COORDENADOR";

    useEffect(() => {
        carregarDados();
    }, []);

    //Busca as campanhas pelo service e atualiza a tabela.
    const carregarDados = async () => {
        try {
            const dados = await buscarListaCampanhas();

            setCampanhas(dados);

        } catch (error) {
            alert("Erro ao carregar dados!");
        }
    }

    const handlerFavoritarCampanha = (campanha: Campanha) => {

        if (campanha.id == null) {
            return;
        }

        const estavaFavoritada = favoritos.includes(campanha.id);

        dispatch(alternarFavorito(campanha.id));

        if (estavaFavoritada) {
            alert("Campanha removida dos favoritos!");
        }
    }

    const handlerDeletarCampanha = async (campanha: Campanha) => {

        var dadosRetorno = await deletarCampanha(campanha.id || 0);

        if (dadosRetorno == 200) {
            alert("Campanha foi deletada com sucesso!");
        } else {
            alert(dadosRetorno);
            return;
        }

        carregarDados();
    }

    //O service altera o status e a página recarrega a tabela.
    const handlerAlterarStatusCampanha = async (campanha: Campanha) => {

        await atualizarStatusCampanha(campanha);

        carregarDados();
    }

    return (
        <div className="bg-purple-50 px-4 py-8 md:px-8">

            <div className="max-w-6xl mx-auto mb-4">
                <Link
                    href="/home"
                    className="inline-block rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700"
                >
                    ← VOLTAR PARA A PÁGINA INICIAL
                </Link>
            </div>

            <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
                <h1 className="text-2xl md:text-3xl font-bold text-purple-800">
                    Gestão de campanhas
                </h1>

                {podeCriar && (
                    <Link
                        href="/campanhas/novo"
                        className="rounded-lg bg-green-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-green-600 active:bg-green-700"
                    >
                        NOVO
                    </Link>
                )}
            </div>

            <div className="max-w-6xl mx-auto">
                <div className="overflow-x-auto rounded-xl border border-purple-100 bg-white shadow-sm">
                    <table className="w-full text-left text-sm">
                        <thead>
                            <tr className="bg-purple-100 text-purple-800">
                                <th className="px-4 py-3 font-semibold">
                                    Favorito
                                </th>
                                <th className="px-4 py-3 font-semibold">Código</th>
                                <th className="px-4 py-3 font-semibold">Título</th>
                                <th className="px-4 py-3 font-semibold">Descrição</th>
                                <th className="px-4 py-3 font-semibold">Meta Financeira</th>
                                <th className="px-4 py-3 font-semibold">Total Arrecadado</th>
                                <th className="px-4 py-3 font-semibold">Data Início</th>
                                <th className="px-4 py-3 font-semibold">Data Fim</th>
                                <th className="px-4 py-3 font-semibold">Status</th>

                                {podeAlterar && (
                                    <th className="px-4 py-3 font-semibold">
                                        Ações
                                    </th>
                                )}
                            </tr>
                        </thead>

                        <tbody>
                            {campanhas.map((campanha) => (
                                <tr
                                    key={campanha.id}
                                    className="border-t border-purple-100 text-gray-700 hover:bg-purple-50"
                                >
                                    <td className="px-4 py-3">
                                        <button
                                            type="button"
                                            onClick={() => handlerFavoritarCampanha(campanha)}
                                            disabled={campanha.id == null}
                                            aria-label={
                                                favoritos.includes(campanha.id || 0)
                                                    ? "Remover dos favoritos"
                                                    : "Adicionar aos favoritos"
                                            }
                                            aria-pressed={favoritos.includes(campanha.id || 0)}
                                            title={
                                                favoritos.includes(campanha.id || 0)
                                                    ? "Remover dos favoritos"
                                                    : "Adicionar aos favoritos"
                                            }
                                            className={`rounded-lg px-2 py-1 text-3xl transition hover:bg-yellow-50 focus:outline-none focus:ring-2 focus:ring-yellow-400 ${
                                                favoritos.includes(campanha.id || 0)
                                                    ? "text-yellow-500"
                                                    : "text-gray-400"
                                            }`}
                                        >
                                            {favoritos.includes(campanha.id || 0)
                                                ? "★"
                                                : "☆"}
                                        </button>
                                    </td>

                                    <td className="px-4 py-3 font-medium text-gray-900">
                                        {campanha.id}
                                    </td>

                                    <td className="px-4 py-3">
                                        {campanha.titulo}
                                    </td>

                                    <td className="px-4 py-3">
                                        {campanha.descricao}
                                    </td>

                                    <td className="px-4 py-3">
                                        {campanha.metaFinanceira}
                                    </td>

                                    <td className="px-4 py-3">
                                        {campanha.totalArrecadado}
                                    </td>

                                    <td className="px-4 py-3">
                                        {campanha.dataInicio}
                                    </td>

                                    <td className="px-4 py-3">
                                        {campanha.dataFim}
                                    </td>

                                    <td className="px-4 py-3">
                                        {campanha.status}
                                    </td>

                                    {podeAlterar && (
                                        <td className="px-4 py-3">
                                            <div className="flex gap-4">
                                                <button className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700">
                                                    <Link href={`/campanhas/${campanha.id}/editar`}>
                                                        EDITAR
                                                    </Link>
                                                </button>

                                                <button
                                                    onClick={() => handlerDeletarCampanha(campanha)}
                                                    className="rounded-lg bg-red-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-red-600"
                                                >
                                                    DELETAR
                                                </button>

                                                <button
                                                    onClick={() => handlerAlterarStatusCampanha(campanha)}
                                                    className={`rounded-lg font-medium transition-colors ${
                                                        campanha.status === 'BLOQUEADO'
                                                            ? 'bg-orange-500 text-white px-4 py-2 text-sm font-semibold hover:text-orange-800'
                                                            : 'bg-green-500 text-white px-4 py-2 text-sm font-semibold hover:text-green-800'
                                                    }`}
                                                >
                                                    {campanha.status}
                                                </button>
                                            </div>
                                        </td>
                                    )}
                                </tr>
                            ))}

                            {campanhas.length === 0 && (
                                <tr>
                                    <td
                                        colSpan={podeAlterar ? 10 : 9}
                                        className="px-6 py-12 text-center text-purple-800"
                                    >
                                        Nenhuma campanha encontrada
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    );
}