export default function Home(){

    //Página inicial simples da área autenticada/sistema. O conteúdo é renderizado dentro do layout
    // `(sistema)`, que acrescenta Sidebar, Header e Footer.
    // Página inicial da área interna do sistema, renderizada dentro do layout do grupo (sistema).
    return(<>
    <div className=" flex items-center justify-center bg-purple-50 px-4">
        <h1 className="text-3xl md:text-4xl font-bold text-purple-500 text-center">Bem vindo ao sistema de Ongs e doações!</h1>
    </div>
    </>);
}