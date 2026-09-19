import Link from "next/link";
import CampanhaForm from "../../components/CampanhaForm";


export default function EditarCampanha(){

    return(
        <div className="min-h-screen bg-purple-50 px-4 py-8 md:px-8">
            <div className="max-w-2xl mx-auto">
                <div className="mb-6">
                    <Link href="/campanhas" className="inline-block text-sm font-medium text-purple-600 hover:text-purple-800 mb-4">
                        &larr; Voltar para Listagem
                    </Link>
                    <div>
                        <h1 className="text-2xl md:text-3xl font-bold text-purple-800">Editar Campanha</h1>
                        <p className="text-sm text-gray-500 mt-1">Preencha os dados para editar uma campanha</p>
                    </div>
                </div>
                <div>
                    <CampanhaForm/>
                </div>
            </div>
        </div>
    );
}