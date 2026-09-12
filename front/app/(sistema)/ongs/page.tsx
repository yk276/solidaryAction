'use client'

import { Ong } from "@/app/types/ong";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Ongs(){

    const [ongs,setOngs] = useState<Ong[]>([]);

    useEffect(()=>{
        carregarDados();
    })

    const carregarDados = async () => {
        try{
            const dados = await axios.get<Ong[]>("http://localhost:8080/ongs");

            setOngs(dados.data);
        } catch (error) {
            alert("Erro ao carregar dados!")
        }
        

    }
    
    return (

        <div className="min-h-screen bg-purple-50 px-4 py-8 md:px-8">

        <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
            <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Gestão de ongs</h1>
            <Link href="/ongs/novo" className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700">Novo</Link>
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
                        </tr>
                    </thead>
                    <tbody>
                        {ongs.map((ong)=> (
                            <tr key={ong.id} className="border-t border-purple-100 text-gray-700 hover:bg-purple-50">
                                <td className="px-4 py-3 font-medium text-gray-900">
                                    {ong.id}
                                </td>
                                <td className="px-4 py-3">
                                    {ong.nome}
                                </td>
                                <td className="px-4 py-3">
                                    {ong.razaoSocial}
                                </td>
                                <td className="px-4 py-3">
                                    {ong.cnpj}
                                </td>
                                <td className="px-4 py-3">
                                    {ong.totalArrecadado}
                                </td>
                                <td className="px-4 py-3">
                                    {ong.email}
                                </td>
                                <td className="px-4 py-3">
                                    {ong.status}
                                </td>
                            </tr>
                            ))}

                                {ongs.length ===0 &&(
                                    <tr>
                                        <td colSpan={5} className= "px-6 py-12 text-center text-purple-800">
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