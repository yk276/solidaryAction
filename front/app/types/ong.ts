export class Ong{
    constructor(
        public id: number | null,
        public nome:string,
        public razaoSocial:string,
        public email:string,
        public cnpj:string,
        public totalArrecadado:number,
        public status:string,
    ){}
}