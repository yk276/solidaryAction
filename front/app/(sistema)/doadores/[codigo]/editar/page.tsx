'use client'

import Link from "next/link";
import DoadorForm from "../../components/DoadorForm";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { Doador } from "@/app/types/doador";
import axios from "axios";


export default function EditarDoador(){

    const parametro = useParams();
    
        const codigo = Number(parametro.codigo);

        const [doador, setDoador] = useState<Doador|null>(null)
    const router = useRouter();

    useEffect(()=> {

        buscarDados();

    },[])

    const buscarDados = async () =>{

        const valorDoadorBack = await axios.get<Doador>('http://localhost:8080/doadores/'+codigo);

        if(valorDoadorBack.status==200){
            setDoador(valorDoadorBack.data)
        }else {
            router.push("/doadores")
        }

    }

    if(!doador) return(<div className="p-8">Carregando Dados...</div>)
    return(
        <div className="min-h-screen bg-purple-50 px-4 py-8 md:px-8">
            <div className="max-w-2xl mx-auto">
                <div className="mb-6">
                    <button className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700 mb-4">
                    <Link href="/doadores" className="inline-block text-sm font-medium">
                        &larr; Voltar para Listagem
                    </Link>
                    </button>
                    
                    <div>
                        <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Editar Doador {codigo}</h1>
                        <p className="text-sm text-gray-500 mt-1">Preencha os dados para editar as informações do doador</p>
                    </div>
                </div>
                <div>
                    <DoadorForm doadorExistente={doador}/>
                </div>
            </div>
        </div>
    );
}