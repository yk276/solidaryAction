import { Ong } from "../types/ong";
import api from "./api";



export async function buscarListaOngs() : Promise<Ong[]> {

    const dados = await api.get<Ong[]>("http://localhost:8080/ongs");

    if(dados.status == 200){
        return dados.data;
    }

    return [];
    
}


export async function deletarOng(id:number) : Promise<number>{

 var dados = await api.delete('http://localhost:8080/ongs/'+id+'/excluir');
    return dados.status;
}


export async function atualizarStatusOng(ong:Ong) :Promise<void>{
   
    var novoStatus = {};
    if(ong.status ==="ATIVO"){
        novoStatus = {status:"BLOQUEADO"}
    }else{
        novoStatus = {status:"ATIVO"}
    }

    var dadosRetorno = await  
    api.patch('http://localhost:8080/ongs/'+ong.id+'/status',novoStatus);

    if(dadosRetorno.status==200){
        alert("Atualizado status com sucesso!");
    }else{
        alert(dadosRetorno.data);

        return;
    } 
}

export async function consultaOngPorId(id:number):Promise<Ong>{
    const valorOngBack = await api.get<Ong>('http://localhost:8080/ongs/'+id);
    return valorOngBack.data;

}

export async function  atualizarOng (ong:Ong): Promise<number>{
    var dadosRetorno = await  
    api.put<number>('http://localhost:8080/ongs/'+ong.id,ong);
    return dadosRetorno.data;
}


export async function  salvarOng (ong:Ong): Promise<number>{
    var dadosRetorno = await  
    api.post<number>('http://localhost:8080/ongs',ong);
    return dadosRetorno.data;
}