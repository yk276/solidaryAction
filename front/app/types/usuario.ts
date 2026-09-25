export class Usuario{
    constructor(
        public id: number | null,
        public nome:string,
        public email:string,
        public status:string,
        public cpf:string,
        public senha:string
    ){}
}
// prova - criar objeto
export interface UsuarioFormProps{
    usuarioExistente?:Usuario
}