'use client'

import { Doacao, DoacaoFormProps } from "@/app/types/doacao";
import {
    atualizarDoacao,
    salvarDoacao,
    tratarErroSalvarDoacao
} from "@/app/services/doacaoService";
import { buscarListaCampanhas } from "@/app/services/campanhaService";
import { buscarListaDoadores } from "@/app/services/doadorService";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

//Dados necessários para preencher os selects.
interface CampanhaOpcao {
    id: number | null;
    titulo: string;
}

interface DoadorOpcao {
    id: number | null;
    nome: string;
}

//Formulário reutilizável para cadastro e edição de doações.
export default function DoacaoForm({doacaoExistente}: DoacaoFormProps){

    const router = useRouter();

    const [doacao, setDoacao] = useState<Doacao>(
        doacaoExistente ||
        new Doacao(null, "", 0, "", "PENDENTE")
    );

    const [campanhas, setCampanhas] = useState<CampanhaOpcao[]>([]);
    const [doadores, setDoadores] = useState<DoadorOpcao[]>([]);

    useEffect(() => {
        carregarOpcoes();
    }, []);

    //Consulta os services para preencher os selects.
    const carregarOpcoes = async () => {
        try {
            const campanhasRetorno = await buscarListaCampanhas();

            const doadoresRetorno = await buscarListaDoadores();

            setCampanhas(campanhasRetorno);
            setDoadores(doadoresRetorno);

        } catch (erro) {
            alert("Não foi possível carregar as campanhas e os doadores.");
        }
    }

    //Atualiza o campo escolhido, preservando os demais.
    const handlerChange = (
        campo: 'dataDoacao' | 'valorDoado' | 'descricao'
            | 'campanhaId' | 'doadorId',
        valor: string
    ) => {
        setDoacao(valorAnterior =>
            new Doacao(
                valorAnterior.id,
                campo === 'dataDoacao'
                    ? valor
                    : valorAnterior.dataDoacao,
                campo === 'valorDoado'
                    ? Number(valor)
                    : valorAnterior.valorDoado,
                campo === 'descricao'
                    ? valor
                    : valorAnterior.descricao,
                valorAnterior.status,
                campo === 'campanhaId'
                    ? (valor === "" ? null : Number(valor))
                    : valorAnterior.campanhaId,
                campo === 'doadorId'
                    ? (valor === "" ? null : Number(valor))
                    : valorAnterior.doadorId,
                valorAnterior.metaAtingida,
                valorAnterior.mensagem
            )
        );
    }

    const handlerSalvar = async (formData: FormData) => {

        if (doacao.campanhaId == null || doacao.doadorId == null) {
            alert("Selecione a campanha e o doador!");
            return;
        }

        try {
            let resposta;

            if (doacaoExistente) {
                resposta = await atualizarDoacao(doacao);
            } else {
                resposta = await salvarDoacao(doacao);
            }

            alert("Doação foi salva com sucesso!");

            if (resposta.metaAtingida) {
                alert(resposta.mensagem);
            }

            router.push("/doacoes");

        } catch (erro) {
            tratarErroSalvarDoacao(erro);
        }
    }

    return (
        <form
            action={handlerSalvar}
            className="max-w-2xl mx-auto bg-white rounded-xl border border-purple-100 shadow-sm p-6 md:p-8"
        >
            <div className="flex flex-col gap-4">

                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Campanha:
                    </label>
                    <select
                        name="campanhaId"
                        value={doacao.campanhaId ?? ""}
                        required
                        onChange={(e) =>
                            handlerChange('campanhaId', e.target.value)
                        }
                        className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200"
                    >
                        <option value="">Selecione a campanha</option>

                        {campanhas.map((campanha) => (
                            <option
                                key={campanha.id}
                                value={campanha.id ?? ""}
                            >
                                {campanha.titulo}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Doador:
                    </label>
                    <select
                        name="doadorId"
                        value={doacao.doadorId ?? ""}
                        required
                        onChange={(e) =>
                            handlerChange('doadorId', e.target.value)
                        }
                        className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200"
                    >
                        <option value="">Selecione o doador</option>

                        {doadores.map((doador) => (
                            <option
                                key={doador.id}
                                value={doador.id ?? ""}
                            >
                                {doador.nome}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Data Doação:
                    </label>
                    <input
                        type="text"
                        name="dataDoacao"
                        value={doacao.dataDoacao}
                        required
                        onChange={(e) =>
                            handlerChange('dataDoacao', e.target.value)
                        }
                        placeholder="Data da doação"
                        className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200"
                    />
                </div>

                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Valor doado:
                    </label>
                    <input
                        name="valorDoado"
                        value={doacao.valorDoado}
                        required
                        onChange={(e) =>
                            handlerChange('valorDoado', e.target.value)
                        }
                        placeholder="Valor da doação"
                        className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200"
                    />
                </div>

                <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Descrição:
                    </label>
                    <input
                        name="descricao"
                        value={doacao.descricao}
                        required
                        onChange={(e) =>
                            handlerChange('descricao', e.target.value)
                        }
                        placeholder="Insira a descrição aqui"
                        className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200"
                    />
                </div>

                <div className="flex items-center justify-end gap-3 mt-2">
                    <Link
                        href="/doacoes"
                        className="rounded-lg border border-purple-200 px-4 py-2 text-sm font-semibold text-purple-700 transition hover:bg-purple-50 active:bg-purple-100"
                    >
                        Cancelar
                    </Link>

                    <button
                        type="submit"
                        className="rounded-lg bg-green-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-green-600 active:bg-green-700"
                    >
                        Salvar
                    </button>
                </div>

            </div>
        </form>
    );
}