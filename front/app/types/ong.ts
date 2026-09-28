//Arquivo de tipagem TypeScript para `ong`. Define o formato dos dados usados no frontend e,
//quando existe, a tipagem das props entregues aos formulários.

//Esta classe TypeScript define o formato usado pelo frontend para representar esta entidade e manter os dados
//tipados.

export class Ong{
    constructor(
        public id: number | null,
        public nomeFantasia:string,
        public razaoSocial:string,
        public email:string,
        public cnpj:string,
        public endereco:string,
        public totalArrecadado:number, // no form é 0 
        public status:string,
    ){}
}

// prova - criar objeto
// A interface/type descreve a estrutura esperada para propriedades ou respostas, permitindo ao TypeScript validar
// o uso dos dados.
export interface OngFormProps{
    ongExistente?:Ong
}