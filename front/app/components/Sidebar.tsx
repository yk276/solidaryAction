'use client'

import Link from "next/link";
import { useSelector } from "react-redux";
import type { RootState } from "../redux/store";

export default function Sidebar() {

    const usuarioLogado = useSelector(
        (state: RootState) => state.auth.usuario
    );

    const podeVerUsuarios =
        usuarioLogado?.role === "ROLE_ADMIN" ||
        usuarioLogado?.role === "ROLE_COORDENADOR";

    return (
        <aside className="w-64 shrink-0 bg-white border-r border-purple-100 min-h-screen flex flex-col">
            <div className="px-6 py-6 text-xl font-bold text-purple-800 border-b border-purple-100">
                SolidaryAction
            </div>

            <nav className="flex flex-col gap-1 p-4">
                <Link
                    href="/home"
                    className="rounded-lg px-4 py-2.5 text-sm font-medium text-gray-600 transition hover:bg-green-50 hover:text-green-700"
                >
                    Home
                </Link>

                {podeVerUsuarios && (
                    <Link
                        href="/usuarios"
                        className="rounded-lg px-4 py-2.5 text-sm font-medium text-gray-600 transition hover:bg-green-50 hover:text-green-700"
                    >
                        Usuários
                    </Link>
                )}

                <Link
                    href="/favoritos"
                    className="rounded-lg px-4 py-2.5 text-sm font-medium text-gray-600 transition hover:bg-green-50 hover:text-green-700"
                >
                    Favoritos
                </Link>

                <Link
                    href="/ongs"
                    className="rounded-lg px-4 py-2.5 text-sm font-medium text-gray-600 transition hover:bg-green-50 hover:text-green-700"
                >
                    Ongs
                </Link>

                <Link
                    href="/campanhas"
                    className="rounded-lg px-4 py-2.5 text-sm font-medium text-gray-600 transition hover:bg-green-50 hover:text-green-700"
                >
                    Campanhas
                </Link>

                <Link
                    href="/doadores"
                    className="rounded-lg px-4 py-2.5 text-sm font-medium text-gray-600 transition hover:bg-green-50 hover:text-green-700"
                >
                    Doadores
                </Link>

                <Link
                    href="/doacoes"
                    className="rounded-lg px-4 py-2.5 text-sm font-medium text-gray-600 transition hover:bg-green-50 hover:text-green-700"
                >
                    Doações
                </Link>
            </nav>
        </aside>
    );
}