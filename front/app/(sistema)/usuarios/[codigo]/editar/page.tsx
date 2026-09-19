'use client'

import Link from "next/link";
import { useParams } from "next/navigation";
import UsuarioForm from "../../components/UsuarioForm";


export default function EditarUsuario(){

    const parametro = useParams();

    const codigo = Number(parametro.codigo);
    return(
        <div className="min-h-screen bg-purple-50 px-4 py-8 md:px-8">
            <div className="max-w-2xl mx-auto">
                <div className="mb-6">
                    <Link href="/usuarios" className="inline-block text-sm font-medium text-purple-600 hover:text-purple-800 mb-4">
                        &larr; Voltar para Listagem
                    </Link>
                    <div>
                        <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Editar Usuário {codigo}</h1>
                        <p className="text-sm text-gray-500 mt-1">Preencha os dados para editar o usuário</p>
                    </div>
                </div>
                <div>
                    <UsuarioForm/>
                </div>
            </div>
        </div>
    );
}