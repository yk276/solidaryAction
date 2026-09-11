export class Doador{
    constructor(
        public id: number | null,
        public nome:string,
        public email:string,
        public profissao:string,
        public status:string,
        public cpf:string
    ){}
}