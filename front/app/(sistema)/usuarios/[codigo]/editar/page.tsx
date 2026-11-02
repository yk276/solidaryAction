'use client'

import { consultaUsuarioPorId } from "@/app/services/usuarioService";
import { Usuario } from "@/app/types/usuario";
import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../../redux/store";
import UsuarioForm from "../../components/UsuarioForm";

//Página de edição de usuário.
export default function EditarUsuario(){

    const parametro = useParams();
    const codigo = Number(parametro.codigo);

    const [usuario, setUsuario] = useState<Usuario | null>(null);
    const router = useRouter();

    const usuarioLogado = useSelector(
        (state: RootState) => state.auth.usuario
    );

    const podeEditar =
        usuarioLogado?.role === "ROLE_ADMIN" ||
        usuarioLogado?.role === "ROLE_COORDENADOR";

    useEffect(() => {
        if (!podeEditar) {
            router.push("/usuarios");
            return;
        }

        //Consulta o usuário somente quando o perfil permite editar.
        const buscarDados = async () => {
            try {
                const valorUsuarioBack = await consultaUsuarioPorId(codigo);

                if (valorUsuarioBack) {
                    setUsuario(valorUsuarioBack);
                } else {
                    router.push("/usuarios");
                }

            } catch (error) {
                alert("Não foi possível carregar o usuário.");
                router.push("/usuarios");
            }
        }

        buscarDados();
    }, [podeEditar, codigo, router]);

    if (!podeEditar) {
        return null;
    }

    if (!usuario) {
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
                            href="/usuarios"
                            className="inline-block text-sm font-medium"
                        >
                            &larr; VOLTAR PARA LISTAGEM
                        </Link>
                    </button>

                    <div>
                        <h1 className="text-2xl md:text-3xl font-bold text-purple-800">
                            Editar Usuário {codigo}
                        </h1>
                        <p className="text-sm text-gray-500 mt-1">
                            Preencha os dados para editar as informações do usuário
                        </p>
                    </div>
                </div>

                <div>
                    <UsuarioForm usuarioExistente={usuario}/>
                </div>
            </div>
        </div>
    );
}