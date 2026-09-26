'use client'

import { Doacao, DoacaoFormProps } from "@/app/types/doacao";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";


export default function DoacaoForm({doacaoExistente}:DoacaoFormProps){

    const router = useRouter();

    const [ doacao, setDoacao] = useState<Doacao>(
        doacaoExistente || 
        new Doacao(null,"",0,"","PENDENTE") // estado inicial
    );

    const handlerChange = ( campo: 'dataDoacao'| 'valorDoado'| 'descricao', valor:string) => {
        setDoacao(valorAnterior => 
            new Doacao(
                valorAnterior.id,
                campo === 'dataDoacao' ? valor : valorAnterior.dataDoacao,
                campo === 'valorDoado' ?  Number (valor) : valorAnterior.valorDoado,
                campo === 'descricao' ? valor : valorAnterior.descricao,
                valorAnterior.status,
            )
        )
    }

    const handlerSalvar = async (formData: FormData) => {
    
        if(doacaoExistente){
            var dadosRetorno = await axios.put<number>('http://localhost:8080/doacoes/'+doacao.id,doacao);
    
           if(dadosRetorno.status==200){
            alert("Doação foi salva com sucesso!")
            
           }else {
            alert(dadosRetorno.data);
    
           }
    
        }else {
           var dadosRetorno = await axios.post<number>('http://localhost:8080/doacoes',doacao)
    
           if(dadosRetorno.status==200){
            alert("Doação foi salva com sucesso!")
            
           }else {
            alert(dadosRetorno.data);
    
           }
        }
           router.push("/doacoes")
        }

    return(
        <form action = {handlerSalvar} className="max-w-2xl mx-auto bg-white rounded-xl border border-purple-100 shadow-sm p-6 md:p-8">
            <div className="flex flex-col gap-4">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Data Doação:
                    </label>
                    <input
                    type="text"
                    name="dataDoacao"
                    value={doacao.dataDoacao}
                    required
                    onChange={(e)=> handlerChange('dataDoacao',e.target.value)}
                    placeholder="Data da doação"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Valor doado:
                    </label>
                    <input
                    name="valorDoado"
                    value={doacao.valorDoado}
                    required
                    onChange={(e)=> handlerChange('valorDoado',e.target.value)}
                    placeholder="Valor da doação"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Descrição:
                    </label>
                    <input
                    name="descricao"
                    value={doacao.descricao}
                    required
                    onChange={(e)=> handlerChange('descricao',e.target.value)}
                    placeholder="Insira a descrição aqui"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>

                <div className="flex items-center justify-end gap-3 mt-2">
                    <Link href="/doacoes" className="rounded-lg border border-purple-200 px-4 py-2 text-sm font-semibold text-purple-700 transition hover:bg-purple-50 active:bg-purple-100">Cancelar</Link>
                    <button type="submit" className="rounded-lg bg-green-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-green-600 active:bg-green-700">Salvar</button>
                </div>
            </div>
        </form>
    );
}