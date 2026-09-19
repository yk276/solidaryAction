

export default function Footer(){


    const anoAtual = new Date().getFullYear();
    return(

        <footer className="bg-white border-t border-purple-100">
            <div className="max-w-6xl mx-auto px-4 py-4 md:px-8">
                <div className="text-center">
                    <p className="text-sm text-gray-500">&copy;{2026}
                    <span className="font-semibold text-purple-700"> SolidaryAction </span>
                    Todos os direitos reservados.
                    </p>
                </div>
            </div>
        </footer>
    );
}