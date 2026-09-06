import Link from "next/link";

export default function Campanhas(){
    
    return (

        <div>

        <div>
            <h1>Gestão de campanhas</h1>
            <Link href="/campanhas/novo"></Link>
        </div>

        <div>
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Título</th>
                            <th>Descrição</th>
                            <th>Meta Financeira</th>
                            <th>Total Arrecadado</th>
                            <th>Data Início</th>
                            <th>Data Fim</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>Campanha de Cestas Básicas</td>
                            <td>Arrecadação de fundos para distribuição de cestas básicas a famílias em situação de vulnerabilidade.</td>
                            <td>R$ 10.000,00</td>
                            <td>R$ 6.450,00</td>
                            <td>01/03/2026</td>
                            <td>30/06/2026</td>
                            <td>Encerrada</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

    )
}