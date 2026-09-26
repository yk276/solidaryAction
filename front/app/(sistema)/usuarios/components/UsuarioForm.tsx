'use client'

import { Usuario, UsuarioFormProps } from "@/app/types/usuario";
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";


export default function UsuarioForm({usuarioExistente}:UsuarioFormProps){
    const router = useRouter();

    const [ usuario, setUsuario] = useState<Usuario>(
        usuarioExistente || 
        new Usuario(null,"","","ATIVO","","") // estado inicial
    );

    //atualização do valor
    const handlerChange = ( campo: 'nome'| 'email'| 'cpf'| 'senha', valor:string) => {
        setUsuario(valorAnterior => 
            new Usuario(
                valorAnterior.id,
                campo === 'nome' ? valor : valorAnterior.nome,
                campo === 'email' ? valor : valorAnterior.email,
                valorAnterior.status,
                campo === 'cpf' ? valor : valorAnterior.cpf,
                campo === 'senha' ? valor : valorAnterior.senha,
            )
        )
    }

    const handlerSalvar = async (formData: FormData) => {

    
    if(usuarioExistente){
        var dadosRetorno = await axios.put<number>('http://localhost:8080/usuarios/'+usuario.id,usuario);

       if(dadosRetorno.status==200){
        alert("Usuário foi salvo com sucesso!")
        
       }else {
        alert(dadosRetorno.data);

       }

    }else {
       var dadosRetorno = await axios.post<number>('http://localhost:8080/usuarios/criar',usuario)

       if(dadosRetorno.status==200){
        alert("Usuário foi salvo com sucesso!")
        
       }else {
        alert(dadosRetorno.data);

       }
    }
       router.push("/usuarios")
    }

    return(
        <form action = {handlerSalvar} className="max-w-2xl mx-auto bg-white rounded-xl border border-purple-100 shadow-sm p-6 md:p-8">
            <div className="flex flex-col gap-4">
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Nome completo:
                    </label>
                    <input
                    name="nome"
                    value={usuario.nome}
                    required
                    onChange={(e)=> handlerChange('nome',e.target.value)}// dispara função
                    placeholder="Seu nome aqui"
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        CPF:
                    </label>
                    <input
                    name="CPF"
                    value={usuario.cpf}
                    required
                    placeholder="000.000.000.-00"
                    onChange={(e)=> handlerChange('cpf',e.target.value)}
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Email:
                    </label>
                    <input
                    name="email"
                    value={usuario.email}
                    required
                    placeholder="seuemail@"
                    onChange={(e)=> handlerChange('email',e.target.value)}
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>
                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Senha:
                    </label>
                    <input
                    name="senha"
                    required
                    placeholder="****"
                    onChange={(e)=> handlerChange('senha',e.target.value)}
                    value={usuario.senha}
                    className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                </div>

                <div className="flex items-center justify-end gap-3 mt-2">
                    <Link href="/usuarios" className="rounded-lg border border-purple-200 px-4 py-2 text-sm font-semibold text-purple-700 transition hover:bg-purple-50 active:bg-purple-100">Cancelar</Link>
                    <button type="submit" className="rounded-lg bg-green-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-green-600 active:bg-green-700">Salvar</button>
                </div>
            </div>
        </form>
    );
}