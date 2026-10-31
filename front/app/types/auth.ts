import { Usuario } from "./usuario";

//Define os dados devolvidos pelo backend após o login.
export interface LoginResponse {
    token: string;
    role: string;
    ongId: number | null;
}

export interface AuthState {
    usuario: Usuario|null
    token: string
}