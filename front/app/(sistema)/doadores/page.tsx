import Link from "next/link";

export default function Doadores(){
    
    return (
    
    <div className="min-h-screen bg-purple-50 px-4 py-8 md:px-8">

        <div className="max-w-6xl mx-auto flex items-center justify-between mb-6">
            <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Gestão de doadores</h1>
            <Link href="/doadores/novo" className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700"></Link>
        </div>

        <div className="max-w-6xl mx-auto">
            <div className="overflow-x-auto rounded-xl border border-purple-100 bg-white shadow-sm">
                <table className="w-full text-left text-sm">
                    <thead>
                        <tr className="bg-purple-100 text-purple-800">
                            <th className="px-4 py-3 font-semibold">Id</th>
                            <th className="px-4 py-3 font-semibold">Nome</th>
                            <th className="px-4 py-3 font-semibold">CPF</th>
                            <th className="px-4 py-3 font-semibold">Email</th>
                            <th className="px-4 py-3 font-semibold">Profissão</th>
                            <th className="px-4 py-3 font-semibold">Endereço</th>
                            <th className="px-4 py-3 font-semibold">Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr className="border-t border-purple-100 text-gray-700 hover:bg-purple-50">
                            <td className="px-4 py-3">1</td>
                            <td className="px-4 py-3 font-medium text-gray-900">Maria Oliveira</td>
                            <td className="px-4 py-3">123.456.789-00</td>
                            <td className="px-4 py-3">maria.oliveira@email.com</td>
                            <td className="px-4 py-3">Professora</td>
                            <td className="px-4 py-3 text-gray-600">Av. Paulista, 1000 - São Paulo/SP</td>
                            <td className="px-4 py-3">
                                <span className="inline-block rounded-full bg-green-100 px-3 py-1 text-xs font-medium text-green-700">Ativo</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

    )
}