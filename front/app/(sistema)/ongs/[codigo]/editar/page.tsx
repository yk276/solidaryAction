'use client'

import Link from "next/link";
import OngForm from "../../components/OngForm";
import { useParams, useRouter } from "next/navigation";
import { Ong } from "@/app/types/ong";
import { useEffect, useState } from "react";
import { consultaOngPorId } from "@/app/services/ongService";
import { useSelector } from "react-redux";
import { RootState } from "../../../../redux/store";

//Página de edição de ONG.
export default function EditarOng(){

    const parametro = useParams();
    const codigo = Number(parametro.codigo);

    const [ong, setOng] = useState<Ong | null>(null);
    const router = useRouter();

    const usuarioLogado = useSelector(
        (state: RootState) => state.auth.usuario
    );

    const podeEditar =
        usuarioLogado?.role === "ROLE_ADMIN" ||
        usuarioLogado?.role === "ROLE_COORDENADOR";

    useEffect(() => {
        if (!podeEditar) {
            router.push("/ongs");
            return;
        }

        //Consulta a ONG somente quando o perfil permite editar.
        const buscarDados = async () => {
            try {
                const valorOngBack = await consultaOngPorId(codigo);

                if (valorOngBack) {
                    setOng(valorOngBack);
                } else {
                    router.push("/ongs");
                }

            } catch (error) {
                alert("Não foi possível carregar a ONG.");
                router.push("/ongs");
            }
        }

        buscarDados();
    }, [podeEditar, codigo, router]);

    if (!podeEditar) {
        return null;
    }

    if (!ong) {
        return (
            <div className="p-8">
                Carregando Dados...
            </div>
        );
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
                            Editar Ong {codigo}
                        </h1>
                        <p className="text-sm text-gray-500 mt-1">
                            Preencha os dados para editar as informações da ong
                        </p>
                    </div>
                </div>

                <div>
                    <OngForm ongExistente={ong}/>
                </div>
            </div>
        </div>
    );
}