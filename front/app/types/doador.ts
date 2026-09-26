export class Doador{
    constructor(
        public id: number | null,
        public nome:string,
        public email:string,
        public profissao:string,
        public endereco:string,
        public status:string,
        public cpf:string
    ){}
}

export interface DoadorFormProps{
    doadorExistente?:Doador
}