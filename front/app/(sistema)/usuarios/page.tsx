'use client'

import { Usuario } from "@/app/types/usuario";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";


export default function Usuarios(){

    const [usuarios,setUsuarios] = useState<Usuario[]>([]);

    useEffect(()=>{
        carregarDados();
    },[])

    const carregarDados = async () => {
        try{
            const dados = await axios.get<Usuario[]>("http://localhost:8080/usuarios");

            setUsuarios(dados.data);
        } catch (error) {
            alert("Erro ao carregar dados!")
        }
        

    }
    
    return (
    
        <div className="bg-purple-50 px-4 py-8 md:px-8">
    
            <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
                <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Gestão de usuários</h1>
                <Link href="/usuarios/novo" className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700">Novo</Link>
            </div>
    
            <div className="max-w-6xl mx-auto">
                <div className="overflow-x-auto rounded-xl border border-purple-100 bg-white shadow-sm">
                    <table className="w-full text-left text-sm">
                        <thead>
                            <tr className="bg-purple-100 text-purple-800">
                                <th className="px-4 py-3 font-semibold">Código</th>
                                <th className="px-4 py-3 font-semibold">Nome</th>
                                <th className="px-4 py-3 font-semibold">CPF</th>
                                <th className="px-4 py-3 font-semibold">Email</th>
                                <th className="px-4 py-3 font-semibold">Status</th>
                                <th className="px-4 py-3 font-semibold">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            {usuarios.map((usuario)=> (
                            <tr key={usuario.id} className="border-t border-purple-100 text-gray-700 hover:bg-purple-50">
                                <td className="px-4 py-3 font-medium text-gray-900">
                                    {usuario.id}
                                </td>
                                <td className="px-4 py-3">
                                    {usuario.nome}
                                </td>
                                <td className="px-4 py-3">
                                    {usuario.cpf}
                                </td>
                                <td className="px-4 py-3">
                                    {usuario.email}
                                </td>
                                <td className="px-4 py-3">
                                    {usuario.status}
                                </td>
                                <td className="px-4 py-3">
                                    <Link href={`/usuarios/${usuario.id}/editar`}>Editar</Link>
                                </td>
                            </tr>
                            ))}

                                {usuarios.length ===0 &&(
                                    <tr>
                                        <td colSpan={5} className= "px-6 py-12 text-center text-purple-800">
                                            Nenhum usuário encontrado
                                        </td>
                                    </tr>
                                )}

                        </tbody>
                    </table>
                </div>
            </div>
    
        </div>
        
        )
}