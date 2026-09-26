export class Doacao{
    constructor(
        public id: number | null,
        public dataDoacao:string,
        public valorDoado:number,
        public descricao:string,
        public status:string
    ){}
}
export interface DoacaoFormProps{
    doacaoExistente?:Doacao
}