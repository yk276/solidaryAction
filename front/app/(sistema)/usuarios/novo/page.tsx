'use client'

import Link from "next/link";
import UsuarioForm from "../components/UsuarioForm";
import { RootState } from "../../../redux/store";
import { useSelector } from "react-redux";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

//Página de cadastro de usuário.
export default function CadastroUsuario(){

    const router = useRouter();

    const usuarioLogado = useSelector(
        (state: RootState) => state.auth.usuario
    );

    const podeCriar =
        usuarioLogado?.role === "ROLE_ADMIN" ||
        usuarioLogado?.role === "ROLE_COORDENADOR";

    useEffect(() => {
        if (!podeCriar) {
            router.push("/usuarios");
        }
    }, [podeCriar, router]);

    if (!podeCriar) {
        return null;
    }

    return (
        <div className="min-h-screen bg-purple-50 px-4 py-8 md:px-8">
            <div className="max-w-2xl mx-auto">
                <div className="mb-6">
                    <button className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700 mb-4">
                        <Link
                            href="/usuarios"
                            className="inline-block text-sm font-medium"
                        >
                            &larr; VOLTAR PARA LISTAGEM
                        </Link>
                    </button>

                    <div>
                        <h1 className="text-2xl md:text-3xl font-bold text-purple-800">
                            Novo Usuário
                        </h1>
                        <p className="text-sm text-gray-500 mt-1">
                            Preencha os dados para registrar um novo usuário
                        </p>
                    </div>
                </div>

                <div>
                    <UsuarioForm/>
                </div>
            </div>
        </div>
    );
}