'use client'

import { Campanha, CampanhaFormProps } from "@/app/types/campanha";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";


export default function CampanhaForm({campanhaExistente}:CampanhaFormProps){

    const router = useRouter();

    const [ campanha, setCampanha] = useState<Campanha>(
        campanhaExistente || 
        new Campanha(null,"","",0,0,"","","ATIVO") // estado inicial
    );

    const handlerChange = ( campo: 'titulo'| 'descricao'| 'metaFinanceira'| 'totalArrecadado' | 'dataInicio' | 'dataFim', valor:string) => {
        setCampanha(valorAnterior => 
            new Campanha(
                valorAnterior.id,
                campo === 'titulo' ? valor : valorAnterior.titulo,
                campo === 'descricao' ? valor : valorAnterior.descricao,
                campo === 'metaFinanceira' ? Number (valor) : valorAnterior.metaFinanceira,
                campo === 'totalArrecadado' ? Number (valor) : valorAnterior.totalArrecadado,
                campo === 'dataInicio' ? valor : valorAnterior.dataInicio,
                campo === 'dataFim' ? valor : valorAnterior.dataFim,
                valorAnterior.status,
                
            )
        )
    }

    const handlerSalvar = async (formData: FormData) => {

    
        if(campanhaExistente){
            var dadosRetorno = await axios.put<number>('http://localhost:8080/campanhas/'+campanha.id,campanha);
    
           if(dadosRetorno.status==200){
            alert("Campanha foi salva com sucesso!")
            
           }else {
            alert(dadosRetorno.data);
    
           }
    
        }else {
           var dadosRetorno = await axios.post<number>('http://localhost:8080/campanhas/criar',campanha)
    
           if(dadosRetorno.status==200){
            alert("Campanha foi salva com sucesso!")
            
           }else {
            alert(dadosRetorno.data);
    
           }
        }
           router.push("/campanhas")
        }

    return(
        <form action = {handlerSalvar} className="max-w-2xl mx-auto bg-white rounded-xl border border-purple-100 shadow-sm p-6 md:p-8">
            <div className="flex flex-col gap-4">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Título:
                    </label>
                    <input
                    name="titulo"
                    value={campanha.titulo}
                    required
                    onChange={(e)=> handlerChange('titulo',e.target.value)}
                    placeholder="Título da campanha"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Descrição:
                    </label>
                    <input
                    name="descricao"
                    value={campanha.descricao}
                    required
                    onChange={(e)=> handlerChange('descricao',e.target.value)}
                    placeholder="Descrição da campanha"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Meta Financeira:
                    </label>
                    <input
                    name="metaFinaneira"
                    value={campanha.metaFinanceira}
                    required
                    onChange={(e)=> handlerChange('metaFinanceira',e.target.value)}
                    placeholder="Meta da campanha"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Total Arrecadado:
                    </label>
                    <input
                    name="totalArrecadado"
                    value={campanha.totalArrecadado}
                    required
                    onChange={(e)=> handlerChange('totalArrecadado',e.target.value)}
                    placeholder="Total Arrecadado"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Data InÍcio:
                    </label>
                    <input
                    name="dataInicio"
                    value={campanha.dataInicio}
                    required
                    onChange={(e)=> handlerChange('dataInicio',e.target.value)}
                    placeholder="Data inicial da campanha"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Data Fim:
                    </label>
                    <input
                    name="dataFim"
                    value={campanha.dataFim}
                    required
                    onChange={(e)=> handlerChange('dataFim',e.target.value)}
                    placeholder="Data final da campanha"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>

                <div className="flex items-center justify-end gap-3 mt-2">
                    <Link href="/campanhas" className="rounded-lg border border-purple-200 px-4 py-2 text-sm font-semibold text-purple-700 transition hover:bg-purple-50 active:bg-purple-100">Cancelar</Link>
                    <button type="submit" className="rounded-lg bg-green-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-green-600 active:bg-green-700">Salvar</button>
                </div>
            </div>
        </form>
    );
}