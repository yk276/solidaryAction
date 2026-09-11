import Link from "next/link";

export default function Campanhas(){
    
    return (

        <div className="min-h-screen bg-purple-50 px-4 py-8 md:px-8">

        <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
            <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Gestão de campanhas</h1>
            <Link href="/campanhas/novo" className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700"></Link>
        </div>

        <div className="max-w-6xl mx-auto">
            <div className="overflow-x-auto rounded-xl border border-purple-100 bg-white shadow-sm">
                <table className="w-full text-left text-sm">
                    <thead>
                        <tr className="bg-purple-100 text-purple-800">
                            <th className="px-4 py-3 font-semibold">Id</th>
                            <th className="px-4 py-3 font-semibold">Título</th>
                            <th className="px-4 py-3 font-semibold">Descrição</th>
                            <th className="px-4 py-3 font-semibold">Meta Financeira</th>
                            <th className="px-4 py-3 font-semibold">Total Arrecadado</th>
                            <th className="px-4 py-3 font-semibold">Data Início</th>
                            <th className="px-4 py-3 font-semibold">Data Fim</th>
                            <th className="px-4 py-3 font-semibold">Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr className="border-t border-purple-100 text-gray-700 hover:bg-purple-50">
                            <td className="px-4 py-3">1</td>
                            <td className="px-4 py-3 font-medium text-gray-900">Campanha de Cestas Básicas</td>
                            <td className="px-4 py-3 text-gray-600">Arrecadação de fundos para distribuição de cestas básicas a famílias em situação de vulnerabilidade.</td>
                            <td className="px-4 py-3">R$ 10.000,00</td>
                            <td className="px-4 py-3">R$ 6.450,00</td>
                            <td className="px-4 py-3">01/03/2026</td>
                            <td className="px-4 py-3">30/06/2026</td>
                            <td className="px-4 py-3">
                                <span className="inline-block rounded-full bg-gray-200 px-3 py-1 text-xs font-medium text-gray-600">Encerrada</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

    )
}