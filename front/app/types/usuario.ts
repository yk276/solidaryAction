//Define os dados do usuário utilizados pelo frontend.
export class Usuario {
    constructor(
        public id: number | null,
        public nome: string,
        public email: string,
        public status: string,
        public cpf: string,
        public senha: string,
        public perfil: string = "U",
        public role: string = "ROLE_USER",
        public ongId: number | null = null
    ) {}
}

export interface UsuarioFormProps {
    usuarioExistente?: Usuario;
}