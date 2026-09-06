import Link from "next/link";

export default function Doadores(){
    
    return (
    
    <div>

        <div>
            <h1>Gestão de doadores</h1>
            <Link href="/doadores/novo"></Link>
        </div>

        <div>
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Nome</th>
                            <th>CPF</th>
                            <th>Email</th>
                            <th>Profissão</th>
                            <th>Endereço</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>Maria Oliveira</td>
                            <td>123.456.789-00</td>
                            <td>maria.oliveira@email.com</td>
                            <td>Professora</td>
                            <td>Av. Paulista, 1000 - São Paulo/SP</td>
                            <td>Ativo</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

    )
}