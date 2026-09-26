'use client'

import { Ong, OngFormProps } from "@/app/types/ong";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";


export default function OngForm({ongExistente}:OngFormProps){

    //prova - implementar salvar e atualizar da ong

    const router = useRouter();

    const [ ong, setOng] = useState<Ong>(
        ongExistente || 
        new Ong(null,"","","","","",0,"ATIVO") // estado inicial
    );

     //atualização do valor
     const handlerChange = ( campo: 'nomeFantasia'| 'razaoSocial'| 'email'| 'cnpj'| 'endereco'| 'totalArrecadado', valor:string) => {
        setOng(valorAnterior => 
            new Ong(
                valorAnterior.id,
                campo === 'nomeFantasia' ? valor : valorAnterior.nomeFantasia,
                campo === 'razaoSocial' ? valor : valorAnterior.razaoSocial,
                campo === 'email' ? valor : valorAnterior.email,
                campo === 'cnpj' ? valor : valorAnterior.cnpj,
                campo === 'endereco' ? valor : valorAnterior.endereco,
                campo === 'totalArrecadado' ? Number (valor) : valorAnterior.totalArrecadado,
                valorAnterior.status,
            )
        )
    }

    const handlerSalvar = async (formData: FormData) => {

    
        if(ongExistente){
            var dadosRetorno = await axios.put<number>('http://localhost:8080/ongs/'+ong.id,ong);
    
           if(dadosRetorno.status==200){
            alert("Ong foi salva com sucesso!")
            
           }else {
            alert(dadosRetorno.data);
    
           }
    
        }else {
           var dadosRetorno = await axios.post<number>('http://localhost:8080/ongs',ong)
    
           if(dadosRetorno.status==200){
            alert("Ong foi salva com sucesso!")
            
           }else {
            alert(dadosRetorno.data);
    
           }
        }
           router.push("/ongs")
        }

    return(
        //prova - validar chamada onsubmit e vinculo dos inputs com o usestate
        <form action = {handlerSalvar} className="max-w-2xl mx-auto bg-white rounded-xl border border-purple-100 shadow-sm p-6 md:p-8">
            <div className="flex flex-col gap-4">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Nome fantasia:
                    </label>
                    <input
                    name="nomeFantasia"
                    value={ong.nomeFantasia}
                    required
                    onChange={(e)=> handlerChange('nomeFantasia',e.target.value)}// dispara função
                    placeholder="Nome da ong"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Razão Social:
                    </label>
                    <input
                    name="razaoSocial"
                    value={ong.razaoSocial}
                    required
                    onChange={(e)=> handlerChange('razaoSocial',e.target.value)}
                    placeholder="Razão social da ong"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        CNPJ:
                    </label>
                    <input
                    name="CNPJ"
                    value={ong.cnpj}
                    required
                    onChange={(e)=> handlerChange('cnpj',e.target.value)}
                    placeholder="CNPJ"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Email:
                    </label>
                    <input
                    name="email"
                    value={ong.email}
                    required
                    onChange={(e)=> handlerChange('email',e.target.value)}
                    placeholder="seu@email"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Endereço:
                    </label>
                    <input
                    name="endereco"
                    value={ong.endereco}
                    required
                    onChange={(e)=> handlerChange('endereco',e.target.value)}
                    placeholder="Endereço"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Total Arrecadado:
                    </label>
                    <input
                    name="totalArrecadado"
                    value={ong.totalArrecadado}
                    required
                    onChange={(e)=> handlerChange('totalArrecadado',e.target.value)}
                    placeholder="Total Arrecadado"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>

                <div className="flex items-center justify-end gap-3 mt-2">
                    <Link href="/ongs" className="rounded-lg border border-purple-200 px-4 py-2 text-sm font-semibold text-purple-700 transition hover:bg-purple-50 active:bg-purple-100">Cancelar</Link>
                    <button type="submit" className="rounded-lg bg-green-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-green-600 active:bg-green-700">Salvar</button>
                </div>
            </div>
        </form>
    );
}