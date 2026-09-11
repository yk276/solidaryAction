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