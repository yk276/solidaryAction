'use client'

import { atualizarStatusOng, buscarListaOngs, deletarOng } from "@/app/services/ongService";
import { Ong } from "@/app/types/ong";
import Link from "next/link";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import type { RootState } from "../../redux/store";

//Tela de listagem e gestão de ONGs. Busca os registros no backend, guarda a lista no estado
//React e oferece ações de editar, excluir logicamente e alterar status.

export default function Ongs(){

    const usuarioLogado = useSelector(
        (state: RootState) => state.auth.usuario
    );

    const administrador = usuarioLogado?role === "ROLE_ADMIN";

    const [ongs,setOngs] = useState<Ong[]>([]);

    useEffect(()=>{
        carregarDados();
    },[])

    //carregarDados faz o GET da coleção no backend e salva o resultado no estado usado pela tabela.
    const carregarDados = async () => {
        try{
            const dados = await buscarListaOngs();

            setOngs(dados);
        } catch (error) {
            alert("Erro ao carregar dados!")
        }
    }

    //prova - implementar alterar status e deletar

    const handlerDeletarOng = async (ong:Ong) => {

        var dadosRetorno = await deletarOng(ong.id||0);

       if(dadosRetorno==200){
        alert("Ong foi deletada com sucesso!")

       }else {
        alert(dadosRetorno);

        return;
       }

       carregarDados();

    }

    //A alteração de status monta o novo valor, envia um PATCH e recarrega os dados para refletir a mudança na tela.
    const handlerAlterarStatusOng = async(ong:Ong) =>{

        await atualizarStatusOng(ong);

        carregarDados();

    }

    // O retorno JSX monta a tabela. O map percorre o array do estado e cria uma linha para cada registro, com ações de
    //editar, excluir e alterar status.
    return (

        <div className="bg-purple-50 px-4 py-8 md:px-8">

        <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
            <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Gestão de ongs</h1>
            <Link href="/ongs/novo" className="rounded-lg bg-green-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-green-600 active:bg-green-700">Novo</Link>
        </div>

        <div className="max-w-6xl mx-auto">
            <div className="overflow-x-auto rounded-xl border border-purple-100 bg-white shadow-sm">
                <table className="w-full text-left text-sm">
                    <thead>
                        <tr className="bg-purple-100 text-purple-800">
                            <th className="px-4 py-3 font-semibold">Código</th>
                            <th className="px-4 py-3 font-semibold">Nome</th>
                            <th className="px-4 py-3 font-semibold">Razão Social</th>
                            <th className="px-4 py-3 font-semibold">CNPJ</th>
                            <th className="px-4 py-3 font-semibold">Email</th>
                            <th className="px-4 py-3 font-semibold">Total Arrecadado</th>
                            <th className="px-4 py-3 font-semibold">Status</th>
                            <th className="px-4 py-3 font-semibold">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {ongs.map((ong)=> (
                            <tr key={ong.id} className="border-t border-purple-100 text-gray-700 hover:bg-purple-50">
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
                                <td className="px-4 py-3 flex gap-4">
                                    {/* prova - adicionar botões necessários */}
                                    <button className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700">
                                    <Link href={`/ongs/${ong.id}/editar`}>Editar</Link>
                                    </button>
                                    <button onClick = {()=> handlerDeletarOng(ong)}
                                       className= "rounded-lg bg-red-500 px-4 py-2 text-sm font-semibold text-white font-medium transition-colors text-red-600 hover:text-red-800 ">
                                        DELETAR</button>
                                        <button onClick = {()=> handlerAlterarStatusOng(ong)}
                                       className= {`rounded-lg font-medium transition-colors ${ong.status ==='BLOQUEADO'
                                         ?'bg-orange-500 text-white px-4 py-2 text-sm font-semibold hover:text-orange-800'
                                         :'bg-green-500 text-white px-4 py-2 text-sm font-semibold hover:bg-green-700' }`
                                         }>{ong.status}</button>
                                </td>
                            </tr>
                            ))}

                                {ongs.length ===0 &&(
                                    <tr>
                                        <td colSpan={7} className= "px-6 py-12 text-center text-purple-800">
                                            Nenhuma ong encontrada
                                        </td>
                                    </tr>
                                )}
                    </tbody>
                </table>
            </div>
        </div>

    </div>

    )
}