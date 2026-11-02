'use client'

import Link from "next/link";
import OngForm from "../components/OngForm";
import { RootState } from "../../../redux/store";
import { useSelector } from "react-redux";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

//Página de cadastro de ONG.
export default function CadastroOng(){

    const router = useRouter();

    const usuarioLogado = useSelector(
        (state: RootState) => state.auth.usuario
    );

    const podeCriar = usuarioLogado?.role === "ROLE_ADMIN";

    useEffect(() => {
        if (!podeCriar) {
            router.push("/ongs");
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
                            href="/ongs"
                            className="inline-block text-sm font-medium"
                        >
                            &larr; VOLTAR PARA LISTAGEM
                        </Link>
                    </button>

                    <div>
                        <h1 className="text-2xl md:text-3xl font-bold text-purple-800">
                            Nova Ong
                        </h1>
                        <p className="text-sm text-gray-500 mt-1">
                            Preencha os dados para registrar uma nova ong
                        </p>
                    </div>
                </div>

                <div>
                    <OngForm/>
                </div>
            </div>
        </div>
    );
}