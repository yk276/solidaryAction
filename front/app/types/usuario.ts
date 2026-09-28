//Arquivo de tipagem TypeScript para `usuario`. Define o formato dos dados usados no frontend e,
//quando existe, a tipagem das props entregues aos formulários.

//Esta classe TypeScript define o formato usado pelo frontend para representar esta entidade e manter os dados
//tipados.

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
//A interface/type descreve a estrutura esperada para propriedades ou respostas, permitindo ao TypeScript validar
//o uso dos dados.
export interface UsuarioFormProps{
    usuarioExistente?:Usuario
}