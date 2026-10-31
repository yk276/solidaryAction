'use client'

import axios from "axios";
import { useRouter } from "next/navigation";
import { LoginResponse } from "../types/auth";
import Cookies from "js-cookie"
import { useDispatch } from "react-redux";
import { buscarUsuarioLogado } from "../services/usuarioService";
import { login } from "../redux/slices/authSlice";


//Tela de login. Lê e-mail e senha do formulário, envia as credenciais ao endpoint de
//autenticação e navega para a área interna quando o backend responde com sucesso.

export default function Login(){
    const router = useRouter();
    const dispatch = useDispatch();

    //Realiza o login e guarda o token, o perfil e a ONG.
    const handleLogin = async (formData: FormData) => {
        try {
            debugger;

            const emailTela = formData.get("email")?.toString() ?? "";
            const senhaTela = formData.get("senha")?.toString() ?? "";

            var loginResposta = await axios.post<LoginResponse>(
                "http://localhost:8080/auth/login",
                {
                    email: emailTela,
                    senha: senhaTela
                }
            );

            if (loginResposta.status == 200) {
                
                
                Cookies.set('token', loginResposta.data.token,{secure: true, expires: 7});
                const token = loginResposta.data.token;

                var usuarioLogado = await buscarUsuarioLogado();
                
                localStorage.setItem("token", token);


                axios.defaults.headers.common["Authorization"] =
                    "Bearer " + token;

                localStorage.setItem("role", loginResposta.data.role);

                if (loginResposta.data.ongId != null) {
                    localStorage.setItem(
                        "ongId",
                        loginResposta.data.ongId.toString()
                    );
                } else {
                    localStorage.removeItem("ongId");
                }
                
                dispatch(login(
                    {
                        token:loginResposta.data.token,
                        usuario:{...usuarioLogado}
                    }
                    ));

                router.push("/home");

            } else {
                alert("Login ou senha inválido!");
            }

        } catch (error) {
            alert("Login ou senha inválido!");
        }
    }

    return(
        <div className="min-h-screen flex items-center justify-center bg-purple-50 px-4">
            <div className="w-full max-w-md bg-white rounded-2xl shadow-sm border border-purple-100 p-8">
                <div>
                    <h1 className="text-2xl font-bold text-purple-500 text-center mb-6">
                        Entrar no sistema
                    </h1>
                </div>

                {/* O formulário entrega os dados à função handleLogin no cliente. */}
                <form action={handleLogin} className="flex flex-col gap-4">

                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-gray-700">
                            E-Mail
                        </label>
                        <input
                            name="email"
                            type="email"
                            className="w-full rounded-lg border border-purple-200 px-3 py-2 text-gray-900 placeholder-gray-400 outline-none transition focus:border-purple-500 focus:ring-2 focus:ring-purple-200">
                        </input>
                    </div>

                    <div className="flex flex-col gap-1">
                        <label className="text-sm font-medium text-gray-700">
                            Senha
                        </label>
                        <input
                            name="senha"
                            type="password"
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