'use client'

import Link from "next/link";
import type { ReactNode } from "react";
import { useSelector } from "react-redux";
import type { RootState } from "../../redux/store";

export default function UsuariosLayout(
    { children }: { children: ReactNode }
) {

    const usuarioLogado = useSelector(
        (state: RootState) => state.auth.usuario
    );

    const podeVerUsuarios =
        usuarioLogado?.role === "ROLE_ADMIN" ||
        usuarioLogado?.role === "ROLE_COORDENADOR";

    if (!podeVerUsuarios) {
        return (
            <div className="max-w-2xl mx-auto rounded-xl border border-purple-100 bg-white p-6 shadow-sm">
                <h1 className="text-2xl font-bold text-purple-800">
                    Acesso não permitido
                </h1>

                <p className="mt-3 text-gray-600">
                    Somente administradores e coordenadores podem
                    acessar a gestão de usuários.
                </p>

                <Link
                    href="/home"
                    className="mt-6 inline-block rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600"
                >
                    VOLTAR PARA A PÁGINA INICIAL
                </Link>
            </div>
        );
    }

    return <>{children}</>;
}