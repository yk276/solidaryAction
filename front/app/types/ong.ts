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
export interface OngFormProps{
    ongExistente?:Ong
}