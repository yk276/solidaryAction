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

    const handlerDeletarUsuario = async (usuario:Usuario) => {

        var dadosRetorno =
        await axios.delete<number>('http://localhost:8080/usuarios/'+usuario.id+'/excluir');

       if(dadosRetorno.status==200){
        alert("Usuário foi salvo com sucesso!")
        
       }else {
        alert(dadosRetorno.data);

        return;
       }

       carregarDados();

    }

    const handlerAlterarStatusUsuario = async(usuario:Usuario) =>{


        var novoStatus = {};
        if(usuario.status ==="ATIVO"){
            novoStatus = {status:"BLOQUEADO"}
        }else{
            novoStatus = {status:"ATIVO"}
        }

        var dadosRetorno = await  
        axios.patch('http://localhost:8080/usuarios/'+usuario.id+'/status',novoStatus);

        if(dadosRetorno.status==200){
            alert("Atulizado status com sucesso!");
        }else{
            alert(dadosRetorno.data);

            return;
        }

        carregarDados();

    }
    
    return (
    
        <div className="bg-purple-50 px-4 py-8 md:px-8">
    
            <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
                <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Gestão de usuários</h1>
                <Link href="/usuarios/novo" className="rounded-lg bg-green-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-green-600 active:bg-green-700">Novo</Link>
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
                                <td className="px-4 py-3 flex gap-4">
                                    <button className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700">
                                    <Link href={`/usuarios/${usuario.id}/editar`}>Editar</Link>
                                    </button>
                                    <button onClick = {()=> handlerDeletarUsuario(usuario)}
                                       className= "rounded-lg bg-red-500 px-4 py-2 text-sm font-semibold text-white font-medium transition-colors text-red-600 hover:text-red-800 ">
                                        DELETAR</button>
                                        <button onClick = {()=> handlerAlterarStatusUsuario(usuario)}
                                       className= {`rounded-lg font-medium transition-colors ${usuario.status ==='BLOQUEADO'
                                         ?'bg-orange-500 text-white px-4 py-2 text-sm font-semibold hover:text-orange-800' 
                                         :'bg-green-500 text-white px-4 py-2 text-sm font-semibold hover:text-green-800' }`
                                         }>{usuario.status}</button>
                                    
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