'use client'

import Link from "next/link";
import CampanhaForm from "../../components/CampanhaForm";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../../redux/store";
import { Campanha } from "@/app/types/campanha";
import { consultaCampanhaPorId } from "@/app/services/campanhaService";

//Página de edição de campanhas.
export default function EditarCampanha(){

    const parametro = useParams();
    const codigo = Number(parametro.codigo);

    const [campanha, setCampanha] = useState<Campanha | null>(null);
    const router = useRouter();

    const usuarioLogado = useSelector(
        (state: RootState) => state.auth.usuario
    );

    const podeEditar = usuarioLogado?.role === "ROLE_COORDENADOR";

    useEffect(() => {
        if (!podeEditar) {
            router.push("/campanhas");
            return;
        }

        //Consulta a campanha somente quando o perfil permite editar.
        const buscarDados = async () => {
            try {
                const valorCampanhaBack = await consultaCampanhaPorId(codigo);

                if (valorCampanhaBack) {
                    setCampanha(valorCampanhaBack);
                } else {
                    router.push("/campanhas");
                }

            } catch (error) {
                alert("Não foi possível carregar a campanha.");
                router.push("/campanhas");
            }
        }

        buscarDados();
    }, [podeEditar, codigo, router]);

    if (!podeEditar) {
        return null;
    }

    if (!campanha) {
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
                            href="/campanhas"
                            className="inline-block text-sm font-medium"
                        >
                            &larr; VOLTAR PARA LISTAGEM
                        </Link>
                    </button>

                    <div>
                        <h1 className="text-2xl md:text-3xl font-bold text-purple-800">
                            Editar Campanha {codigo}
                        </h1>
                        <p className="text-sm text-gray-500 mt-1">
                            Preencha os dados para editar as informações da campanha
                        </p>
                    </div>
                </div>

                <div>
                    <CampanhaForm campanhaExistente={campanha}/>
                </div>
            </div>
        </div>
    );
}