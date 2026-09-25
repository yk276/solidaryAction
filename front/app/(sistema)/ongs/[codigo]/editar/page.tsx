'use client'

import Link from "next/link";
import OngForm from "../../components/OngForm";
import { useParams, useRouter } from "next/navigation";
import { Ong } from "@/app/types/ong";
import { useEffect, useState } from "react";
import axios from "axios";



export default function EditarOng(){

    const parametro = useParams();
    
        const codigo = Number(parametro.codigo);

        //prova - implementar a consulta por id da ong para carregar o formulário

        const [ong, setOng] = useState<Ong|null>(null)
    const router = useRouter();

    useEffect(()=> {

        buscarDados();

    },[])

    const buscarDados = async () =>{

        const valorOngBack = await axios.get<Ong>('http://localhost:8080/ongs/'+codigo);

        if(valorOngBack.status==200){
            setOng(valorOngBack.data)
        }else {
            router.push("/ongs")
        }

    }

    if(!ong) return(<div className="p-8">Carregando Dados...</div>)

    return(
        <div className="min-h-screen bg-purple-50 px-4 py-8 md:px-8">
            <div className="max-w-2xl mx-auto">
                <div className="mb-6">
                    <button className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700 mb-4">
                        <Link href="/ongs" className="inline-block text-sm font-medium">
                        &larr; Voltar para Listagem
                    </Link>
                    </button>
                    
                    <div>
                        <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Editar Ong {codigo}</h1>
                        <p className="text-sm text-gray-500 mt-1">Preencha os dados para editar as informações da ong</p>
                    </div>
                </div>
                <div>
                    {/*prova - passar a ong carregada pelo id para o novo formulário */}
                    <OngForm ongExistente={ong}/>
                </div>
            </div>
        </div>
    );
}