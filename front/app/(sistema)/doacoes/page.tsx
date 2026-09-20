'use client'

import Link from "next/link";

import { Doacao } from "@/app/types/doacao";
import axios from "axios";
import { useEffect, useState } from "react";

export default function Doacoes(){

    const [doacoes,setDoacoes] = useState<Doacao[]>([]);

    useEffect(()=>{
        carregarDados();
    },[])

    const carregarDados = async () => {
        try{
            const dados = await axios.get<Doacao[]>("http://localhost:8080/doacoes");

            setDoacoes(dados.data);
        } catch (error) {
            alert("Erro ao carregar dados!")
        }
        

    }
    
    return (
        <div className=" bg-purple-50 px-4 py-8 md:px-8">

        <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
            <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Gestão de doações</h1>
            <Link href="/doacoes/novo" className="rounded-lg bg-green-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-green-600 active:bg-green-700">Novo</Link>
        </div>

        <div className="max-w-6xl mx-auto">
            <div className="overflow-x-auto rounded-xl border border-purple-100 bg-white shadow-sm">
                <table className="w-full text-left text-sm">
                    <thead>
                        <tr className="bg-purple-100 text-purple-800">
                            <th className="px-4 py-3 font-semibold">Código</th>
                            <th className="px-4 py-3 font-semibold">Data Doação</th>
                            <th className="px-4 py-3 font-semibold">Valor Doado</th>
                            <th className="px-4 py-3 font-semibold">Descrição</th>
                            <th className="px-4 py-3 font-semibold">Status</th>
                            <th className="px-4 py-3 font-semibold">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {doacoes.map((doacao)=> (
                            <tr key={doacao.id} className="border-t border-purple-100 text-gray-700 hover:bg-purple-50">
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
                                    <button className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700">
                                    <Link href={`/doacoes/${doacao.id}/editar`}>Editar</Link>
                                    </button>
                                </td>
                            </tr>
                            ))}

                                {doacoes.length ===0 &&(
                                    <tr>
                                        <td colSpan={5} className= "px-6 py-12 text-center text-purple-800">
                                            Nenhuma doacão encontrada
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