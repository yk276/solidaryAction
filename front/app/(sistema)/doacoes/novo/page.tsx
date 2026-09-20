import Link from "next/link";
import DoacaoForm from "../components/DoacaoForm";

export default function CadastroDoacao(){

    return(
        <div className="min-h-screen bg-purple-50 px-4 py-8 md:px-8">
            <div className="max-w-2xl mx-auto">
                <div className="mb-6">
                    <button className="rounded-lg bg-purple-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-purple-600 active:bg-purple-700 mb-4">
                    <Link href="/doacoes" className="inline-block text-sm font-medium">
                        &larr; Voltar para Listagem
                    </Link>
                    </button>
                    
                    <div>
                        <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Nova Doação</h1>
                        <p className="text-sm text-gray-500 mt-1">Preencha os dados para registrar uma nova doação</p>
                    </div>
                </div>
                <div>
                    <DoacaoForm/>
                </div>
            </div>
        </div>
    );
}