//Arquivo de tipagem TypeScript para `campanha`. Define o formato dos dados usados no frontend
//e, quando existe, a tipagem das props entregues aos formulários.

//Esta classe TypeScript define o formato usado pelo frontend para representar esta entidade e manter os dados
//tipados.

export class Campanha{
    constructor(
        public id: number | null,
        public titulo:string,
        public descricao:string,
        public metaFinanceira:number,
        public totalArrecadado:number,
        public dataInicio:string,
        public dataFim:string,
        public status:string
    ){}
}
// A interface/type descreve a estrutura esperada para propriedades ou respostas, permitindo ao TypeScript validar
// o uso dos dados.
export interface CampanhaFormProps{
    campanhaExistente?:Campanha
}