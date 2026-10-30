'use client'

import Link from "next/link";
import OngForm from "../../components/OngForm";
import { useParams, useRouter } from "next/navigation";
import { Ong } from "@/app/types/ong";
import { useEffect, useState } from "react";
import { consultaOngPorId } from "@/app/services/ongService";

//Página de edição de ONG. Lê o código da rota dinâmica, busca o registro correspondente no
//backend e só depois entrega os dados ao formulário reutilizável para edição.

export default function EditarOng(){

    const parametro = useParams();
    
        const codigo = Number(parametro.codigo);

        //prova - implementar a consulta por id da ong para carregar o formulário

        const [ong, setOng] = useState<Ong|null>(null)
    const router = useRouter();

    useEffect(()=> {

        buscarDados();

    },[])

    // Esta função consulta o backend pelo código da URL. Se encontrar o registro, grava os dados no estado; caso
    //contrário, redireciona o usuário.
    const buscarDados = async () =>{

        const valorOngBack = await consultaOngPorId(codigo);

        if(valorOngBack){
            setOng(valorOngBack)
        }else {
            router.push("/ongs")
        }

    }

    if(!ong) return(<div className="p-8">Carregando Dados...</div>)

    {/* Depois que os dados chegam, a tela renderiza o formulário reutilizável em modo de edição, passando o objeto */}
    {/* existente como propriedade. */}
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