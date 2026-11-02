import Link from "next/link";

//Página inicial do sistema.
//Apresenta uma mensagem de boas-vindas e atalhos para as listagens.
export default function Home(){

    return (
        <div className="max-w-6xl mx-auto space-y-8">

            <div className="rounded-2xl border border-purple-100 bg-white p-6 shadow-sm md:p-10">
                

                <h1 className="mt-5 text-2xl font-bold text-purple-800 md:text-4xl">
                    Bem-vindo ao sistema!
                </h1>

                <p className="mt-4 max-w-2xl text-base leading-7 text-gray-600">
                    Um espaço para visualizar campanhas, acompanhar doações
                    e verificar os favoritos.
                </p>
            </div>

            <div>
                <h2 className="text-xl font-bold text-purple-800">
                    Acessos rápidos:
                </h2>

                <p className="mt-2 text-sm text-gray-500">
                    Escolha uma das opções para acessar os registros do sistema.
                </p>

                <div className="mt-5 grid grid-cols-1 gap-5 md:grid-cols-2">

                    <Link
                        href="/campanhas"
                        className="rounded-xl border border-purple-100 border-t-4 border-t-purple-500 bg-white p-6 shadow-sm transition hover:bg-purple-50 hover:shadow-md"
                    >
                        <h3 className="text-lg font-bold text-purple-800">
                            Campanhas
                        </h3>

                        <p className="mt-3 text-sm leading-6 text-gray-600">
                            Conheça as campanhas e consulte suas metas
                            e os valores arrecadados.
                        </p>

                        <span className="mt-5 inline-block text-sm font-semibold text-purple-700">
                            VER CAMPANHAS →
                        </span>
                    </Link>

                    <Link
                        href="/doadores"
                        className="rounded-xl border border-green-100 border-t-4 border-t-green-500 bg-white p-6 shadow-sm transition hover:bg-green-50 hover:shadow-md"
                    >
                        <h3 className="text-lg font-bold text-green-800">
                            Doadores
                        </h3>

                        <p className="mt-3 text-sm leading-6 text-gray-600">
                            Consulte os dados de quem contribui
                            para tornar essas ações possíveis.
                        </p>

                        <span className="mt-5 inline-block text-sm font-semibold text-green-700">
                            VER DOADORES →
                        </span>
                    </Link>

                    <Link
                        href="/doacoes"
                        className="rounded-xl border border-green-100 border-t-4 border-t-green-500 bg-white p-6 shadow-sm transition hover:bg-green-50 hover:shadow-md"
                    >
                        <h3 className="text-lg font-bold text-green-800">
                            Doações
                        </h3>

                        <p className="mt-3 text-sm leading-6 text-gray-600">
                            Acompanhe as contribuições registradas
                            e consulte a situação de cada doação.
                        </p>

                        <span className="mt-5 inline-block text-sm font-semibold text-green-700">
                            VER DOAÇÕES →
                        </span>
                    </Link>

                    <Link
                        href="/favoritos"
                        className="rounded-xl border border-purple-100 border-t-4 border-t-purple-500 bg-white p-6 shadow-sm transition hover:bg-purple-50 hover:shadow-md"
                    >
                        <h3 className="text-lg font-bold text-purple-800">
                            Favoritos
                        </h3>

                        <p className="mt-3 text-sm leading-6 text-gray-600">
                            Acesse as campanhas que você marcou
                            como favoritas.
                        </p>

                        <span className="mt-5 inline-block text-sm font-semibold text-purple-700">
                            VER FAVORITOS →
                        </span>
                    </Link>

                </div>
            </div>

        </div>
    );
}