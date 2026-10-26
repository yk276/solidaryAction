//Define os dados da doação utilizados pelo frontend.
export class Doacao {
    constructor(
        public id: number | null,
        public dataDoacao: string,
        public valorDoado: number,
        public descricao: string,
        public status: string,
        public campanhaId: number | null = null,
        public doadorId: number | null = null,
        public metaAtingida: boolean = false,
        public mensagem: string = ""
    ) {}
}

export interface DoacaoFormProps {
    doacaoExistente?: Doacao;
}