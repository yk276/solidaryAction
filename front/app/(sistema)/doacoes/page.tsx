'use client'

import Link from "next/link";
import { Doacao } from "@/app/types/doacao";
import {
    atualizarStatusDoacao,
    buscarListaDoacoes,
    deletarDoacao
} from "@/app/services/doacaoService";
import { useEffect, useState } from "react";

//Tela de listagem e gestão de doações.
export default function Doacoes(){

    const [doacoes, setDoacoes] = useState<Doacao[]>([]);
    const [podeAlterar, setPodeAlterar] = useState(false);

    useEffect(() => {
        const role = localStorage.getItem("role");

        setPodeAlterar(
            role === "ROLE_ADMIN" || role === "ROLE_COORDENADOR"
        );

        carregarDados();
    }, []);

    //Busca as doações pelo service e atualiza a tabela.
    const carregarDados = async () => {
        try {
            const dados = await buscarListaDoacoes();

            setDoacoes(dados);

        } catch (erro) {
            alert("Erro ao carregar dados!");
        }
    }

    const handlerDeletarDoacao = async (doacao: Doacao) => {
        try {
            await deletarDoacao(doacao.id || 0);

            alert("Doação foi deletada com sucesso!");

            carregarDados();

        } catch (erro) {
            alert("Não foi possível excluir a doação.");
        }
    }

    //O service altera o status e apresenta o alerta de meta.
    const handlerAlterarStatusDoacao = async (doacao: Doacao) => {

        if (doacao.status === "EXCLUIDO") {
            return;
        }

        await atualizarStatusDoacao(doacao);

        carregarDados();
    }

    return (
        <div className="bg-purple-50 px-4 py-8 md:px-8">

            <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
                <h1 className="text-2xl md:text-3xl font-bold text-purple-800">
                    Gestão de doações
                </h1>

                {podeAlterar && (
                    <Link
                        href="/doacoes/novo"
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
                                    Data Doação
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Valor Doado
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Descrição
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Status
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Ações
                                </th>
                            </tr>
                        </thead>

                        <tbody>
                            {doacoes.map((doacao) => (
                                <tr
                                    key={doacao.id}
                                    className="border-t border-purple-100 text-gray-700 hover:bg-purple-50"
                                >
                                    <td className="px-4 py-3 font-medium text-gray-900">
                                        {doacao.id}
                                    </td>

                                    <td className="px-4 py-3">
                                        {doacao.dataDoacao}
                                    </td>

                                    <td className="px-4 py-3">
                                        {doacao.valorDoado}
                                    </td>

                                    <td className="px-4 py-3">
                                        {doacao.descricao}
                                    </td>

                                    <td className="px-4 py-3">
                                        {doacao.status}
                                    </td>

                                    <td className="px-4 py-3">
                                        {podeAlterar && doacao.status !== "EXCLUIDO" && (
                                            <div className="flex gap-4">
                                                <Link
                                                    href={`/doacoes/${doacao.id}/editar`}
                                                    className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700"
                                                >
                                                    EDITAR
                                                </Link>

                                                <button
                                                    onClick={() =>
                                                        handlerDeletarDoacao(doacao)
                                                    }
                                                    className="rounded-lg bg-red-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-red-600"
                                                >
                                                    DELETAR
                                                </button>

                                                <button
                                                    onClick={() =>
                                                        handlerAlterarStatusDoacao(doacao)
                                                    }
                                                    className={`rounded-lg px-4 py-2 text-sm font-semibold text-white ${
                                                        doacao.status === "CONFIRMADO"
                                                            ? "bg-orange-500 hover:bg-orange-600"
                                                            : "bg-green-500 hover:bg-green-600"
                                                    }`}
                                                >
                                                    {doacao.status}
                                                </button>
                                            </div>
                                        )}
                                    </td>
                                </tr>
                            ))}

                            {doacoes.length === 0 && (
                                <tr>
                                    <td
                                        colSpan={6}
                                        className="px-6 py-12 text-center text-purple-800"
                                    >
                                        Nenhuma doação encontrada
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