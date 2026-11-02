'use client'

import {
    atualizarStatusUsuario,
    buscarListaUsuarios,
    deletarUsuario
} from "@/app/services/usuarioService";
import { Usuario } from "@/app/types/usuario";
import { RootState } from "../../redux/store";
import Link from "next/link";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";

//Tela de listagem e gestão de usuários.
export default function Usuarios(){

    const [usuarios, setUsuarios] = useState<Usuario[]>([]);

    const usuarioLogado = useSelector(
        (state: RootState) => state.auth.usuario
    );

    const podeAlterar =
        usuarioLogado?.role === "ROLE_ADMIN" ||
        usuarioLogado?.role === "ROLE_COORDENADOR";

    useEffect(() => {
        carregarDados();
    }, []);

    //Busca os usuários pelo service e atualiza a tabela.
    const carregarDados = async () => {
        try {
            const dados = await buscarListaUsuarios();

            setUsuarios(dados);

        } catch (error) {
            alert("Erro ao carregar dados!");
        }
    }

    const handlerDeletarUsuario = async (usuario: Usuario) => {

        var dadosRetorno = await deletarUsuario(usuario.id || 0);

        if (dadosRetorno == 200) {
            alert("Usuário foi deletado com sucesso!");
        } else {
            alert(dadosRetorno);
            return;
        }

        carregarDados();
    }

    //O service altera o status e a página recarrega a tabela.
    const handlerAlterarStatusUsuario = async (usuario: Usuario) => {

        await atualizarStatusUsuario(usuario);

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
                    Gestão de usuários
                </h1>

                {podeAlterar && (
                    <Link
                        href="/usuarios/novo"
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
                                    CPF
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Email
                                </th>
                                <th className="px-4 py-3 font-semibold">
                                    Status
                                </th>

                                {podeAlterar && (
                                    <th className="px-4 py-3 font-semibold">
                                        Ações
                                    </th>
                                )}
                            </tr>
                        </thead>

                        <tbody>
                            {usuarios.map((usuario) => (
                                <tr
                                    key={usuario.id}
                                    className="border-t border-purple-100 text-gray-700 hover:bg-purple-50"
                                >
                                    <td className="px-4 py-3 font-medium text-gray-900">
                                        {usuario.id}
                                    </td>

                                    <td className="px-4 py-3">
                                        {usuario.nome}
                                    </td>

                                    <td className="px-4 py-3">
                                        {usuario.cpf}
                                    </td>

                                    <td className="px-4 py-3">
                                        {usuario.email}
                                    </td>

                                    <td className="px-4 py-3">
                                        {usuario.status}
                                    </td>

                                    {podeAlterar && (
                                        <td className="px-4 py-3">
                                            <div className="flex gap-4">
                                                <button className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700">
                                                    <Link href={`/usuarios/${usuario.id}/editar`}>
                                                        EDITAR
                                                    </Link>
                                                </button>

                                                <button
                                                    onClick={() => handlerDeletarUsuario(usuario)}
                                                    className="rounded-lg bg-red-500 px-4 py-2 text-sm font-semibold text-white font-medium transition-colors text-red-600 hover:text-red-800"
                                                >
                                                    DELETAR
                                                </button>

                                                <button
                                                    onClick={() => handlerAlterarStatusUsuario(usuario)}
                                                    className={`rounded-lg font-medium transition-colors ${
                                                        usuario.status === 'BLOQUEADO'
                                                            ? 'bg-orange-500 text-white px-4 py-2 text-sm font-semibold hover:text-orange-800'
                                                            : 'bg-green-500 text-white px-4 py-2 text-sm font-semibold hover:text-green-800'
                                                    }`}
                                                >
                                                    {usuario.status}
                                                </button>
                                            </div>
                                        </td>
                                    )}
                                </tr>
                            ))}

                            {usuarios.length === 0 && (
                                <tr>
                                    <td
                                        colSpan={podeAlterar ? 6 : 5}
                                        className="px-6 py-12 text-center text-purple-800"
                                    >
                                        Nenhum usuário encontrado
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