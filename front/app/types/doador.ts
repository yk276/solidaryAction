//Arquivo de tipagem TypeScript para `doador`. Define o formato dos dados usados no frontend e,
//quando existe, a tipagem das props entregues aos formulários.

//Esta classe TypeScript define o formato usado pelo frontend para representar esta entidade e manter os dados
// tipados.

export class Doador{
    constructor(
        public id: number | null,
        public nome:string,
        public email:string,
        public profissao:string,
        public endereco:string,
        public status:string,
        public cpf:string,
        public ongId: number | null = null
    ){}
}

//A interface/type descreve a estrutura esperada para propriedades ou respostas, permitindo ao TypeScript validar
//o uso dos dados.
export interface DoadorFormProps{
    doadorExistente?:Doador
}