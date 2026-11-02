'use client'

import { useEffect, useState } from "react";
import type { ReactNode } from "react";
import { useRouter } from "next/navigation";
import Footer from "../components/Footer";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import { RootState } from "../redux/store";
import { useSelector } from "react-redux";

export default function SistemaLayout(
    {children}: {children: ReactNode}
){
    const [pronto, setPronto] = useState(false);

    const usuario = useSelector(
        (state: RootState) => state.auth.usuario
    );

    const token = useSelector(
        (state: RootState) => state.auth.token
    );

    const router = useRouter();

    //Espera o componente carregar no navegador.
    useEffect(() => {
        setPronto(true);
    }, []);

    useEffect(() => {
        if (!pronto) {
            return;
        }

        if (usuario == null || !token) {
            router.replace("/login");
        }
    }, [pronto, usuario, token, router]);

    if (!pronto) {
        return <p className="p-4">Carregando...</p>;
    }

    if (usuario == null || !token) {
        return <p className="p-4">Redirecionando para o login...</p>;
    }

    return (
        <div className="flex min-h-screen bg-purple-50">
            <Sidebar/>

            <div className="flex flex-1 flex-col min-w-0">
                <Header/>

                <main className="flex-1 overflow-y-auto p-4 md:p-8">
                    {children}
                </main>

                <Footer/>
            </div>
        </div>
    );
}