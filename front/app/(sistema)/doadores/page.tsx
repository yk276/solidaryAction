'use client'

import Link from "next/link";

import { Doador } from "@/app/types/doador";
import axios from "axios";
import { useEffect, useState } from "react";

export default function Doadores(){

    const [doadores,setDoadores] = useState<Doador[]>([]);
    
        useEffect(()=>{
            carregarDados();
        },[])

        const carregarDados = async () => {
        try{
            const dados = await axios.get<Doador[]>("http://localhost:8080/doadores");

            setDoadores(dados.data);
        } catch (error) {
            alert("Erro ao carregar dados!")
        }
        

    }

    const handlerDeletarDoador = async (doador:Doador) => {

        var dadosRetorno =
        await axios.delete<number>('http://localhost:8080/doadores/'+doador.id+'/excluir');

       if(dadosRetorno.status==200){
        alert("Doador foi deletado com sucesso!")
        
       }else {
        alert(dadosRetorno.data);

        return;
       }

       carregarDados();

    }

    const handlerAlterarStatusDoador = async(doador:Doador) =>{


        var novoStatus = {};
        if(doador.status ==="ATIVO"){
            novoStatus = {status:"BLOQUEADO"}
        }else{
            novoStatus = {status:"ATIVO"}
        }

        var dadosRetorno = await  
        axios.patch('http://localhost:8080/doadores/'+doador.id+'/status',novoStatus);

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
            <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Gestão de doadores</h1>
            <Link href="/doadores/novo" className="rounded-lg bg-green-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-green-600 active:bg-green-700">Novo</Link>
        </div>

        <div className="max-w-6xl mx-auto">
            <div className="overflow-x-auto rounded-xl border border-purple-100 bg-white shadow-sm">
                <table className="w-full text-left text-sm">
                    <thead>
                        <tr className="bg-purple-100 text-purple-800">
                            <th className="px-4 py-3 font-semibold">Código</th>
                            <th className="px-4 py-3 font-semibold">Nome</th>
                            <th className="px-4 py-3 font-semibold">CPF</th>
                            <th className="px-4 py-3 font-semibold">Email</th>
                            <th className="px-4 py-3 font-semibold">Profissão</th>
                            <th className="px-4 py-3 font-semibold">Status</th>
                            <th className="px-4 py-3 font-semibold">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {doadores.map((doador)=> (
                            <tr key={doador.id} className="border-t border-purple-100 text-gray-700 hover:bg-purple-50">
                                <td className="px-4 py-3 font-medium text-gray-900">
                                    {doador.id}
                                </td>
                                <td className="px-4 py-3">
                                    {doador.nome}
                                </td>
                                <td className="px-4 py-3">
                                    {doador.cpf}
                                </td>
                                <td className="px-4 py-3">
                                    {doador.email}
                                </td>
                                 <td className="px-4 py-3">
                                    {doador.profissao}
                                </td>
                                <td className="px-4 py-3">
                                    {doador.status}
                                </td>
                                <td className="px-4 py-3 flex gap-4">
                                    <button className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700">
                                    <Link href={`/doadores/${doador.id}/editar`}>Editar</Link>
                                    </button>
                                    <button onClick = {()=> handlerDeletarDoador(doador)}
                                       className= "rounded-lg bg-red-500 px-4 py-2 text-sm font-semibold text-white font-medium transition-colors text-red-600 hover:text-red-800 ">
                                        DELETAR</button>
                                        <button onClick = {()=> handlerAlterarStatusDoador(doador)}
                                       className= {`rounded-lg font-medium transition-colors ${doador.status ==='BLOQUEADO'
                                         ?'bg-orange-500 text-white px-4 py-2 text-sm font-semibold hover:text-orange-800' 
                                         :'bg-green-500 text-white px-4 py-2 text-sm font-semibold hover:text-green-800' }`
                                         }>{doador.status}</button>
                                </td>
                            </tr>
                            ))}

                                {doadores.length ===0 &&(
                                    <tr>
                                        <td colSpan={7} className= "px-6 py-12 text-center text-purple-800">
                                            Nenhum doador encontrado
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