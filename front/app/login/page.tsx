'use client'

import { useRouter } from "next/navigation";


export default function Login(){
    const router = useRouter();

    const handleLogin = async(formData:FormData) => {
        router.push("/home")
    }

    return(
        <div className="min-h-screen flex items-center justify-center bg-purple-50 px-4">
            <div className="w-full max-w-md bg-white rounded-2xl shadow-sm border border-purple-100 p-8">
                <div>
                    <h1 className="text-2xl font-bold text-purple-500 text-center mb-6">
                        Entrar no sistema
                    </h1>
                </div>
                <form action={handleLogin} className="flex flex-col gap-4">

                   <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        E-Mail
                    </label>
                    <input
                        name="email"
                        className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                   </div>
                   <div className="flex flex-col gap-1">
                    <label className="text-sm font-medium text-gray-700">
                        Senha
                    </label>
                    <input
                        name="senha"
                        className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                    </input>
                   </div>

                   <button
                    type="submit"
                    className="mt-2 w-full rounded-lg bg-purple-400 py-2.5 font-semibold text-white transition hover:bg-purple-400 active:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-purple-300 focus:ring-offset-2">
                    Entrar
                   </button>

                </form>
            </div>
        </div>

    );
}