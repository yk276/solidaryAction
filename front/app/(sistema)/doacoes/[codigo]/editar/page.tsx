'use client'

import { Doacao } from "@/app/types/doacao";
import axios from "axios";
import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import DoacaoForm from "../../components/DoacaoForm";


export default function EditarDoacao(){

    const parametro = useParams();

    const codigo = Number(parametro.codigo);

    const [doacao, setDoacao] = useState<Doacao|null>(null)
    const router = useRouter();

    useEffect(()=> {

        buscarDados();

    },[])

    const buscarDados = async () =>{

        const valorDoacaoBack = await axios.get<Doacao>('http://localhost:8080/doacoes/'+codigo);

        if(valorDoacaoBack.status==200){
            setDoacao(valorDoacaoBack.data)
        }else {
            router.push("/doacoes")
        }

    }

    if(!doacao) return(<div className="p-8">Carregando Dados...</div>)

    return(
        <div className="min-h-screen bg-purple-50 px-4 py-8 md:px-8">
            <div className="max-w-2xl mx-auto">
                <div className="mb-6">
                    <button className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700 mb-4">
                    <Link href="/doacoes" className="inline-block text-sm font-medium">
                        &larr; Voltar para Listagem
                    </Link>
                    </button>
                    
                    <div>
                        <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Editar Doação {codigo}</h1>
                        <p className="text-sm text-gray-500 mt-1">Preencha os dados para editar as informações da doação</p>
                    </div>
                </div>
                <div>
                    <DoacaoForm doacaoExistente={doacao}/>
                </div>
            </div>
        </div>
    );
}