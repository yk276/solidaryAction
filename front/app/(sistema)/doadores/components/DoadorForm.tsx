'use client'

import { Doador, DoadorFormProps } from "@/app/types/doador";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";


export default function DoadorForm({doadorExistente}:DoadorFormProps){

    const router = useRouter();

    const [ doador, setDoador] = useState<Doador>(
        doadorExistente || 
        new Doador(null,"","","","","ATIVO","") // estado inicial
    );

    const handlerChange = ( campo: 'nome'| 'email'| 'profissao'| 'endereco' | 'cpf', valor:string) => {
        setDoador(valorAnterior => 
            new Doador(
                valorAnterior.id,
                campo === 'nome' ? valor : valorAnterior.nome,
                campo === 'email' ? valor : valorAnterior.email,
                campo === 'profissao' ? valor : valorAnterior.profissao,
                campo === 'endereco' ? valor : valorAnterior.endereco,
                valorAnterior.status,
                campo === 'cpf' ? valor : valorAnterior.cpf,
            )
        )
    }

    const handlerSalvar = async (formData: FormData) => {

        debugger;
        if(doadorExistente){
            var dadosRetorno = await axios.put<number>('http://localhost:8080/doadores/'+doador.id,doador);
    
           if(dadosRetorno.status==200){
            alert("Doador foi salvo com sucesso!")
            
           }else {
            alert(dadosRetorno.data);
    
           }
    
        }else {
           var dadosRetorno = await axios.post<number>('http://localhost:8080/doadores',doador)
    
           if(dadosRetorno.status==200){
            alert("Doador foi salvo com sucesso!")
            
           }else {
            alert(dadosRetorno.data);
    
           }
        }
           router.push("/doadores")
        }

    return(
        <form action = {handlerSalvar} className="max-w-2xl mx-auto bg-white rounded-xl border border-purple-100 shadow-sm p-6 md:p-8">
            <div className="flex flex-col gap-4">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Nome completo:
                    </label>
                    <input
                    name="nome"
                    value={doador.nome}
                    required
                    onChange={(e)=> handlerChange('nome',e.target.value)}
                    placeholder="Seu nome aqui"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        CPF:
                    </label>
                    <input
                    name="CPF"
                    value={doador.cpf}
                    required
                    onChange={(e)=> handlerChange('cpf',e.target.value)}
                    placeholder="000.000.000.-00"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Email:
                    </label>
                    <input
                    name="email"
                    value={doador.email}
                    required
                    onChange={(e)=> handlerChange('email',e.target.value)}
                    placeholder="seu@email"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Profissão:
                    </label>
                    <input
                    name="profissao"
                    value={doador.profissao}
                    required
                    onChange={(e)=> handlerChange('profissao',e.target.value)}
                    placeholder="Profissão"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Endereço:
                    </label>
                    <input
                    name="endereco"
                    value={doador.endereco}
                    required
                    onChange={(e)=> handlerChange('endereco',e.target.value)}
                    placeholder="Endereço"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>

                <div className="flex items-center justify-end gap-3 mt-2">
                    <Link href="/doadores" className="rounded-lg border border-purple-200 px-4 py-2 text-sm font-semibold text-purple-700 transition hover:bg-purple-50 active:bg-purple-100">Cancelar</Link>
                    <button type="submit" className="rounded-lg bg-green-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-green-600 active:bg-green-700">Salvar</button>
                </div>
            </div>
        </form>
    );
}