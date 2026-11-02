'use client'

import { buscarListaCampanhas } from "@/app/services/campanhaService";
import { Campanha } from "@/app/types/campanha";
import { RootState } from "../../redux/store";
import { alternarFavorito } from "../../redux/slices/favoritosSlice";
import Link from "next/link";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";

export default function Favoritos(){

    const [campanhas, setCampanhas] = useState<Campanha[]>([]);
    const [carregando, setCarregando] = useState(true);
    const [erroCarregamento, setErroCarregamento] = useState(false);

    const dispatch = useDispatch();

    const favoritos = useSelector(
        (state: RootState) => state.favoritos.campanhasIds
    );

    useEffect(() => {
        carregarDados();
    }, []);

    //Busca somente as campanhas que o usuário pode consultar.
    const carregarDados = async () => {
        try {
            const dados = await buscarListaCampanhas();

            setCampanhas(dados);

        } catch (error) {
            setErroCarregamento(true);
            alert("Erro ao carregar campanhas favoritas!");

        } finally {
            setCarregando(false);
        }
    }

    const campanhasFavoritas = campanhas.filter(
        (campanha) =>
            campanha.id != null &&
            favoritos.includes(campanha.id)
    );

    const handlerRemoverFavorito = (campanha: Campanha) => {

        if (campanha.id == null) {
            return;
        }

        dispatch(alternarFavorito(campanha.id));

        alert("Campanha removida dos favoritos!");
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
                    Minhas campanhas favoritas
                </h1>

                <Link
                    href="/campanhas"
                    className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600"
                >
                    VER CAMPANHAS
                </Link>
            </div>

            <div className="max-w-6xl mx-auto">
                <div className="overflow-x-auto rounded-xl border border-purple-100 bg-white shadow-sm">
                    <table className="w-full text-left text-sm">
                        <thead>
                            <tr className="bg-purple-100 text-purple-800">
                                <th className="px-4 py-3 font-semibold">
                                    Favorito
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Código
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Título
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Descrição
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Meta Financeira
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Total Arrecadado
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Status
                                </th>
                            </tr>
                        </thead>

                        <tbody>
                            {!carregando && !erroCarregamento && (
                                campanhasFavoritas.map((campanha) => (
                                    <tr
                                        key={campanha.id}
                                        className="border-t border-purple-100 text-gray-700 hover:bg-purple-50"
                                    >
                                        <td className="px-4 py-3">
                                            <button
                                                type="button"
                                                onClick={() =>
                                                    handlerRemoverFavorito(campanha)
                                                }
                                                aria-label="Remover dos favoritos"
                                                aria-pressed={true}
                                                title="Remover dos favoritos"
                                                className="rounded-lg px-2 py-1 text-3xl text-yellow-500 transition hover:bg-yellow-50 focus:outline-none focus:ring-2 focus:ring-yellow-400"
                                            >
                                                ★
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
                                            {campanha.status}
                                        </td>
                                    </tr>
                                ))
                            )}

                            {carregando && (
                                <tr>
                                    <td
                                        colSpan={7}
                                        className="px-6 py-12 text-center text-purple-800"
                                    >
                                        Carregando favoritos...
                                    </td>
                                </tr>
                            )}

                            {!carregando && erroCarregamento && (
                                <tr>
                                    <td
                                        colSpan={7}
                                        className="px-6 py-12 text-center text-purple-800"
                                    >
                                        Não foi possível carregar os favoritos.
                                    </td>
                                </tr>
                            )}

                            {!carregando &&
                                !erroCarregamento &&
                                campanhasFavoritas.length === 0 && (
                                    <tr>
                                        <td
                                            colSpan={7}
                                            className="px-6 py-12 text-center text-purple-800"
                                        >
                                            Nenhuma campanha favoritada
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