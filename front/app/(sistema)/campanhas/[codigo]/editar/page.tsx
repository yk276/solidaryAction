'use client'

import Link from "next/link";
import CampanhaForm from "../../components/CampanhaForm";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { Campanha } from "@/app/types/campanha";
import axios from "axios";


export default function EditarCampanha(){

    const parametro = useParams();
    
        const codigo = Number(parametro.codigo);

        const [campanha, setCampanha] = useState<Campanha|null>(null)
    const router = useRouter();

    useEffect(()=> {

        buscarDados();

    },[])

    const buscarDados = async () =>{

        const valorCampanhaBack = await axios.get<Campanha>('http://localhost:8080/campanhas/'+codigo);

        if(valorCampanhaBack.status==200){
            setCampanha(valorCampanhaBack.data)
        }else {
            router.push("/ongs")
        }

    }

    if(!campanha) return(<div className="p-8">Carregando Dados...</div>)
    return(
        <div className="min-h-screen bg-purple-50 px-4 py-8 md:px-8">
            <div className="max-w-2xl mx-auto">
                <div className="mb-6">
                    <button className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700 mb-4">
                        <Link href="/campanhas" className="inline-block text-sm font-medium">
                        &larr; Voltar para Listagem
                    </Link>
                    </button>
                    
                    <div>
                        <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Editar Campanha {codigo}</h1>
                        <p className="text-sm text-gray-500 mt-1">Preencha os dados para editar as informações da campanha</p>
                    </div>
                </div>
                <div>
                    <CampanhaForm campanhaExistente={campanha}/>
                </div>
            </div>
        </div>
    );
}