//Arquivo de tipagem TypeScript para `doacao`. Define o formato dos dados usados no frontend e,
//quando existe, a tipagem das props entregues aos formulários.

//Esta classe TypeScript define o formato usado pelo frontend para representar esta entidade e manter os dados
//tipados.

export class Doacao{
    constructor(
        public id: number | null,
        public dataDoacao:string,
        public valorDoado:number,
        public descricao:string,
        public status:string
    ){}
}
//A interface/type descreve a estrutura esperada para propriedades ou respostas, permitindo ao TypeScript validar
//o uso dos dados.
export interface DoacaoFormProps{
    doacaoExistente?:Doacao
}