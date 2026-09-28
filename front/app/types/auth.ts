//Arquivo de tipagem TypeScript para `auth`. Define o formato dos dados usados no frontend e,
//quando existe, a tipagem das props entregues aos formulários.

//A interface/type descreve a estrutura esperada para propriedades ou respostas, permitindo ao TypeScript validar
//o uso dos dados.
export interface LoginResponse{
    token:string
}