'use client'

import { Doacao } from "@/app/types/doacao";
import { consultaDoacaoPorId } from "@/app/services/doacaoService";
import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../../redux/store";
import DoacaoForm from "../../components/DoacaoForm";

//Página de edição de doação.
export default function EditarDoacao(){

    const parametro = useParams();
    const codigo = Number(parametro.codigo);

    const [doacao, setDoacao] = useState<Doacao | null>(null);
    const router = useRouter();

    const usuarioLogado = useSelector(
        (state: RootState) => state.auth.usuario
    );

    const podeEditar = usuarioLogado?.role === "ROLE_COORDENADOR";

    useEffect(() => {
        if (!podeEditar) {
            router.push("/doacoes");
            return;
        }

        //Consulta a doação somente quando o perfil permite editar.
        const buscarDados = async () => {
            try {
                const valorDoacaoBack = await consultaDoacaoPorId(codigo);

                if (valorDoacaoBack) {
                    setDoacao(valorDoacaoBack);
                } else {
                    router.push("/doacoes");
                }

            } catch (error) {
                alert("Não foi possível carregar a doação.");
                router.push("/doacoes");
            }
        }

        buscarDados();
    }, [podeEditar, codigo, router]);

    if (!podeEditar) {
        return null;
    }

    if (!doacao) {
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
                            href="/doacoes"
                            className="inline-block text-sm font-medium"
                        >
                            &larr; VOLTAR PARA LISTAGEM
                        </Link>
                    </button>

                    <div>
                        <h1 className="text-2xl md:text-3xl font-bold text-purple-800">
                            Editar Doação {codigo}
                        </h1>
                        <p className="text-sm text-gray-500 mt-1">
                            Preencha os dados para editar as informações da doação
                        </p>
                    </div>
                </div>

                <div>
                    <DoacaoForm doacaoExistente={doacao}/>
                </div>
            </div>
        </div>
    );
}