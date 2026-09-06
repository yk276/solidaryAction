import Link from "next/link";

export default function Ongs(){
    
    return (

        <div>

        <div>
            <h1>Gestão de ongs</h1>
            <Link href="/ongs/novo"></Link>
        </div>

        <div>
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Nome</th>
                            <th>Razão Social</th>
                            <th>CNPJ</th>
                            <th>Email</th>
                            <th>Endereço</th>
                            <th>Total Arrecadado</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>Instituto Esperança</td>
                            <td>Instituto Esperança de Apoio Social</td>
                            <td>12.345.678/0001-90</td>
                            <td>contato@institutoesperanca.org.br</td>
                            <td>Rua das Flores, 123 - Centro, São Paulo/SP</td>
                            <td>R$ 6.450,00</td>
                            <td>Ativa</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

    )
}