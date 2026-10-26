'use client'

import axios from "axios";
import { useEffect, useState } from "react";
import type { ReactNode } from "react";
import { useRouter } from "next/navigation";
import Footer from "../components/Footer";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";

export default function SistemaLayout(
    {children}: {children: ReactNode}
){
    const router = useRouter();

    const [autenticacaoPronta, setAutenticacaoPronta] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem("token");

        if (!token) {
            delete axios.defaults.headers.common["Authorization"];
            router.replace("/login");
            return;
        }

        axios.defaults.headers.common["Authorization"] =
            "Bearer " + token;

        setAutenticacaoPronta(true);
    }, [router]);

    //Espera configurar o token antes de carregar as telas internas.
    if (!autenticacaoPronta) {
        return <p className="p-4">Carregando...</p>;
    }

    return(
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