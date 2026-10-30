'use client'

import {
    atualizarUsuario,
    salvarUsuario
} from "@/app/services/usuarioService";
import { buscarListaOngs } from "@/app/services/ongService";
import { Usuario, UsuarioFormProps } from "@/app/types/usuario";
import { Ong } from "@/app/types/ong";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function UsuarioForm({usuarioExistente}: UsuarioFormProps){

    const router = useRouter();

    const [roleLogado, setRoleLogado] = useState("");
    const [ongs, setOngs] = useState<Ong[]>([]);

    const administrador = roleLogado === "ROLE_ADMIN";
    const registroAdmin = usuarioExistente?.role === "ROLE_ADMIN";

    const [usuario, setUsuario] = useState<Usuario>(
        usuarioExistente
            ? new Usuario(
                usuarioExistente.id,
                usuarioExistente.nome,
                usuarioExistente.email,
                usuarioExistente.status,
                usuarioExistente.cpf,
                "",
                usuarioExistente.role === "ROLE_COORDENADOR" ? "C" : "U",
                usuarioExistente.role,
                usuarioExistente.ongId
            )
            : new Usuario(null, "", "", "ATIVO", "", "", "U")
    );

    useEffect(() => {
        const role = localStorage.getItem("role") ?? "";

        setRoleLogado(role);

        if (role === "ROLE_ADMIN" && !usuarioExistente) {

            //O novo cadastro realizado pelo ADMIN é de Coordenador.
            setUsuario(valorAnterior =>
                new Usuario(
                    valorAnterior.id,
                    valorAnterior.nome,
                    valorAnterior.email,
                    valorAnterior.status,
                    valorAnterior.cpf,
                    valorAnterior.senha,
                    "C",
                    valorAnterior.role,
                    valorAnterior.ongId
                )
            );

            carregarOngs();
        }
    }, []);

    const carregarOngs = async () => {
        try {
            const dados = await buscarListaOngs();

            setOngs(dados);

        } catch (erro) {
            alert("Não foi possível carregar as ONGs!");
        }
    }

    const handlerChange = (
        campo: 'nome' | 'email' | 'cpf' | 'senha' | 'perfil' | 'ongId',
        valor: string
    ) => {
        setUsuario(valorAnterior =>
            new Usuario(
                valorAnterior.id,
                campo === 'nome' ? valor : valorAnterior.nome,
                campo === 'email' ? valor : valorAnterior.email,
                valorAnterior.status,
                campo === 'cpf' ? valor : valorAnterior.cpf,
                campo === 'senha' ? valor : valorAnterior.senha,
                campo === 'perfil' ? valor : valorAnterior.perfil,
                valorAnterior.role,
                campo === 'ongId'
                    ? (valor === "" ? null : Number(valor))
                    : valorAnterior.ongId
            )
        );
    }

    const handlerSalvar = async (formData: FormData) => {

        if (
            administrador &&
            !usuarioExistente &&
            usuario.ongId == null
        ) {
            alert("Selecione a ONG do coordenador!");
            return;
        }

        if (usuarioExistente) {
            var dadosRetorno = await atualizarUsuario(usuario);

            if (dadosRetorno) {
                alert("Usuário foi salvo com sucesso!");
            } else {
                alert("Erro ao atualizar usuário!");
                return;
            }

        } else {
            var dadosRetorno = await salvarUsuario(usuario);

            if (dadosRetorno) {
                alert("Usuário foi salvo com sucesso!");
            } else {
                alert("Erro ao salvar usuário!");
                return;
            }
        }

        router.push("/usuarios");
    }

    //Aguarda a identificação do perfil antes de exibir o formulário.
    if (roleLogado === "") {
        return (
            <div className="p-8">
                Carregando Dados...
            </div>
        );
    }

    return (
        <form
            action={handlerSalvar}
            className="max-w-2xl mx-auto bg-white rounded-xl border border-purple-100 shadow-sm p-6 md:p-8"
        >
            <div className="flex flex-col gap-4">

                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Nome completo:
                    </label>
                    <input
                        name="nome"
                        value={usuario.nome}
                        required
                        onChange={(e) =>
                            handlerChange('nome', e.target.value)
                        }
                        placeholder="Seu nome aqui"
                        className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200"
                    />
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
                        onChange={(e) =>
                            handlerChange('cpf', e.target.value)
                        }
                        className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200"
                    />
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
                        onChange={(e) =>
                            handlerChange('email', e.target.value)
                        }
                        className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200"
                    />
                </div>

                {(!usuarioExistente || administrador) && (
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-gray-700">
                            Senha:
                        </label>
                        <input
                            name="senha"
                            type="password"
                            required={!usuarioExistente}
                            placeholder={
                                usuarioExistente
                                    ? "Deixe em branco para manter a senha atual"
                                    : "Digite a senha"
                            }
                            onChange={(e) =>
                                handlerChange('senha', e.target.value)
                            }
                            value={usuario.senha}
                            className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200"
                        />
                    </div>
                )}

                {!registroAdmin && (
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-gray-700">
                            Perfil:
                        </label>

                        <select
                            name="perfil"
                            value={usuario.perfil}
                            required
                            disabled={!administrador}
                            onChange={(e) =>
                                handlerChange('perfil', e.target.value)
                            }
                            className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200"
                        >
                            {usuarioExistente ? (
                                <>
                                    <option value="C">Coordenador</option>
                                    <option value="U">Usuário</option>
                                </>
                            ) : administrador ? (
                                <option value="C">Coordenador</option>
                            ) : (
                                <option value="U">Usuário</option>
                            )}
                        </select>
                    </div>
                )}

                {administrador && !usuarioExistente && (
                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-gray-700">
                            ONG:
                        </label>

                        <select
                            name="ongId"
                            value={usuario.ongId ?? ""}
                            required
                            onChange={(e) =>
                                handlerChange('ongId', e.target.value)
                            }
                            className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200"
                        >
                            <option value="">Selecione a ONG</option>

                            {ongs.map((ong) => (
                                <option
                                    key={ong.id}
                                    value={ong.id ?? ""}
                                >
                                    {ong.nomeFantasia}
                                </option>
                            ))}
                        </select>
                    </div>
                )}

                <div className="flex items-center justify-end gap-3 mt-2">
                    <Link
                        href="/usuarios"
                        className="rounded-lg border border-purple-200 px-4 py-2 text-sm font-semibold text-purple-700 transition hover:bg-purple-50 active:bg-purple-100"
                    >
                        CANCELAR
                    </Link>

                    <button
                        type="submit"
                        className="rounded-lg bg-green-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-green-600 active:bg-green-700"
                    >
                        SALVAR
                    </button>
                </div>

            </div>
        </form>
    );
}