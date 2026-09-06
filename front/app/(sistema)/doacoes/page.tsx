import Link from "next/link";

export default function Doacoes(){
    
    return (
        <div>

        <div>
            <h1>Gestão de doações</h1>
            <Link href="/docacoes/novo"></Link>
        </div>

        <div>
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Data Doação</th>
                            <th>Valor Doado</th>
                            <th>Descrição</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>10/03/2026</td>
                            <td>R$ 2.000,00</td>
                            <td>Doação anônima via PIX</td>
                            <td>Concluída</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
    )
}