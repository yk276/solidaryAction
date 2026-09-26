'use client'

import { Campanha } from "@/app/types/campanha";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Campanhas(){
    
    const [campanhas,setCampanhas] = useState<Campanha[]>([]);

    useEffect(()=>{
        carregarDados();
    },[]);

    const carregarDados = async () => {
        try{
            const dados = await axios.get<Campanha[]>("http://localhost:8080/campanhas");

            setCampanhas(dados.data);
        } catch (error) {
            alert("Erro ao carregar dados!")
        }
        

    }

    const handlerDeletarCampanha = async (campanha:Campanha) => {

        var dadosRetorno =
        await axios.delete<number>('http://localhost:8080/campanhas/'+campanha.id+'/excluir');

       if(dadosRetorno.status==200){
        alert("Campanha foi deletada com sucesso!")
        
       }else {
        alert(dadosRetorno.data);

        return;
       }

       carregarDados();

    }

    const handlerAlterarStatusCampanha = async(campanha:Campanha) =>{


        var novoStatus = {};
        if(campanha.status ==="ATIVO"){
            novoStatus = {status:"BLOQUEADO"}
        }else{
            novoStatus = {status:"ATIVO"}
        }

        var dadosRetorno = await  
        axios.patch('http://localhost:8080/campanhas/'+campanha.id+'/status',novoStatus);

        if(dadosRetorno.status==200){
            alert("Atulizado status com sucesso!");
        }else{
            alert(dadosRetorno.data);

            return;
        }

        carregarDados();

    }


    return (

        <div className="bg-purple-50 px-4 py-8 md:px-8">

        <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
            <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Gestão de campanhas</h1>
            <Link href="/campanhas/novo" className="rounded-lg bg-green-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-green-600 active:bg-green-700">Novo</Link>
        </div>

        <div className="max-w-6xl mx-auto">
            <div className="overflow-x-auto rounded-xl border border-purple-100 bg-white shadow-sm">
                <table className="w-full text-left text-sm">
                    <thead>
                        <tr className="bg-purple-100 text-purple-800">
                            <th className="px-4 py-3 font-semibold">Código</th>
                            <th className="px-4 py-3 font-semibold">Título</th>
                            <th className="px-4 py-3 font-semibold">Descrição</th>
                            <th className="px-4 py-3 font-semibold">Meta Financeira</th>
                            <th className="px-4 py-3 font-semibold">Total Arrecadado</th>
                            <th className="px-4 py-3 font-semibold">Data Início</th>
                            <th className="px-4 py-3 font-semibold">Data Fim</th>
                            <th className="px-4 py-3 font-semibold">Status</th>
                            <th className="px-4 py-3 font-semibold">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {campanhas.map((campanha)=> (
                            <tr key={campanha.id} className="border-t border-purple-100 text-gray-700 hover:bg-purple-50">
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
                                <td className="px-4 py-3 flex gap-4">
                                    <button className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700">
                                    <Link href={`/campanhas/${campanha.id}/editar`}>Editar</Link>
                                    </button>
                                    <button onClick = {()=> handlerDeletarCampanha(campanha)}
                                       className= "rounded-lg bg-red-500 px-4 py-2 text-sm font-semibold text-white font-medium transition-colors text-red-600 hover:text-red-800 ">
                                        DELETAR</button>
                                        <button onClick = {()=> handlerAlterarStatusCampanha(campanha)}
                                       className= {`rounded-lg font-medium transition-colors ${campanha.status ==='BLOQUEADO'
                                         ?'bg-orange-500 text-white px-4 py-2 text-sm font-semibold hover:text-orange-800' 
                                         :'bg-green-500 text-white px-4 py-2 text-sm font-semibold hover:text-green-800' }`
                                         }>{campanha.status}</button>
                                </td>
                            </tr>
                            ))}

                                {campanhas.length ===0 &&(
                                    <tr>
                                        <td colSpan={8} className= "px-6 py-12 text-center text-purple-800">
                                            Nenhuma campanha encontrada
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