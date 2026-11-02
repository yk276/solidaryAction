'use client'

import {
    atualizarStatusOng,
    buscarListaOngs,
    deletarOng
} from "@/app/services/ongService";
import { Ong } from "@/app/types/ong";
import { RootState } from "../../redux/store";
import Link from "next/link";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";

// Tela de listagem e gestão de ONGs.
export default function Ongs() {

    const [ongs, setOngs] = useState<Ong[]>([]);

    const usuarioLogado = useSelector(
        (state: RootState) => state.auth.usuario
    );

    const administrador = usuarioLogado?.role === "ROLE_ADMIN";

    const podeEditar =
        administrador || usuarioLogado?.role === "ROLE_COORDENADOR";

    useEffect(() => {
        carregarDados();
    }, []);

    // Busca as ONGs permitidas para o usuário logado.
    const carregarDados = async () => {
        try {
            const dados = await buscarListaOngs();

            setOngs(dados);
        } catch (error) {
            alert("Erro ao carregar dados!");
        }
    }

    const handlerDeletarOng = async (ong: Ong) => {

        var dadosRetorno = await deletarOng(ong.id || 0);

        if (dadosRetorno == 200) {
            alert("Ong foi deletada com sucesso!");
        } else {
            alert(dadosRetorno);

            return;
        }

        carregarDados();
    }

    const handlerAlterarStatusOng = async (ong: Ong) => {

        await atualizarStatusOng(ong);

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
                    Gestão de ongs
                </h1>

                {administrador && (
                    <Link
                        href="/ongs/novo"
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
                                    Código
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Nome
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Razão Social
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    CNPJ
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Email
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Total Arrecadado
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Status
                                </th>

                                {podeEditar && (
                                    <th className="px-4 py-3 font-semibold">
                                        Ações
                                    </th>
                                )}
                            </tr>
                        </thead>

                        <tbody>
                            {ongs.map((ong) => (
                                <tr
                                    key={ong.id}
                                    className="border-t border-purple-100 text-gray-700 hover:bg-purple-50"
                                >
                                    <td className="px-4 py-3 font-medium text-gray-900">
                                        {ong.id}
                                    </td>
                                    <td className="px-4 py-3">
                                        {ong.nomeFantasia}
                                    </td>
                                    <td className="px-4 py-3">
                                        {ong.razaoSocial}
                                    </td>
                                    <td className="px-4 py-3">
                                        {ong.cnpj}
                                    </td>
                                    <td className="px-4 py-3">
                                        {ong.email}
                                    </td>
                                    <td className="px-4 py-3">
                                        {ong.totalArrecadado}
                                    </td>
                                    <td className="px-4 py-3">
                                        {ong.status}
                                    </td>

                                    {podeEditar && (
                                        <td className="px-4 py-3">
                                            <div className="flex gap-4">
                                                <Link
                                                    href={`/ongs/${ong.id}/editar`}
                                                    className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700"
                                                >
                                                    EDITAR
                                                </Link>

                                                {administrador && (
                                                    <>
                                                        <button
                                                            onClick={() => handlerDeletarOng(ong)}
                                                            className="rounded-lg bg-red-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-red-600 active:bg-red-700"
                                                        >
                                                            DELETAR
                                                        </button>

                                                        <button
                                                            onClick={() => handlerAlterarStatusOng(ong)}
                                                            className={`rounded-lg px-4 py-2 text-sm font-semibold text-white transition ${
                                                                ong.status === "BLOQUEADO"
                                                                    ? "bg-orange-500 hover:bg-orange-600"
                                                                    : "bg-green-500 hover:bg-green-700"
                                                            }`}
                                                        >
                                                            {ong.status}
                                                        </button>
                                                    </>
                                                )}
                                            </div>
                                        </td>
                                    )}
                                </tr>
                            ))}

                            {ongs.length === 0 && (
                                <tr>
                                    <td
                                        colSpan={podeEditar ? 8 : 7}
                                        className="px-6 py-12 text-center text-purple-800"
                                    >
                                        Nenhuma ong encontrada
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