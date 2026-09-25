'use client'

import { Usuario } from "@/app/types/usuario";
import axios from "axios";
import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import UsuarioForm from "../../components/UsuarioForm";


export default function EditarUsuario(){

    const parametro = useParams();

    const codigo = Number(parametro.codigo);

    const [usuario, setUsuario] = useState<Usuario|null>(null)
    const router = useRouter();

    useEffect(()=> {

        buscarDados();

    },[])

    const buscarDados = async () =>{

        const valorUsuarioBack = await axios.get<Usuario>('http://localhost:8080/usuarios/'+codigo);

        if(valorUsuarioBack.status==200){
            setUsuario(valorUsuarioBack.data)
        }else {
            router.push("/usuarios")
        }

    }

    if(!usuario) return(<div className="p-8">Carregando Dados...</div>)

    return(
        <div className="min-h-screen bg-purple-50 px-4 py-8 md:px-8">
            <div className="max-w-2xl mx-auto">
                <div className="mb-6">
                    <button className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700 mb-4">
                    <Link href="/usuarios" className="inline-block text-sm font-medium">
                        &larr; Voltar para Listagem
                    </Link>
                    </button>
                    <div>
                        <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Editar Usuário {codigo}</h1>
                        <p className="text-sm text-gray-500 mt-1">Preencha os dados para editar as informações do usuário</p>
                    </div>
                </div>
                <div>
                    <UsuarioForm usuarioExistente={usuario}/>
                </div>
            </div>
        </div>
    );
}