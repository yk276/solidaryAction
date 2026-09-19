

export default function Header(){


    return(
        <header className="bg-white border-b border-purple-100 shadow-sm">

        <div className="max-w-6xl mx-auto flex items-center justify-between px-4 py-4 md:px-8">
            <div className="flex items-center gap-2">
                <svg xmlns="http://www.w3.org/2000/svg" className="h-8 w-8 text-purple-600" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth={1.8} strokeLinecap="round" strokeLinejoin="round">
                    <path d="M12 21s-6.716-4.35-9.428-8.06C.86 10.24 1.2 6.9 3.6 5.1c2.2-1.65 5.1-1.2 6.7.9L12 7.8l1.7-1.8c1.6-2.1 4.5-2.55 6.7-.9 2.4 1.8 2.74 5.14.53 7.84C18.716 16.65 12 21 12 21z"/>
                    <path d="M8.5 12.5l1.8 1.8L15.5 9"/>
                </svg>
                <span className="text-lg font-bold text-purple-800">Solidary<span className="text-green-500">Action</span></span>
            </div>

            <div className="flex items-center gap-2 text-purple-800">
                <div className="flex items-center justify-center rounded-full bg-purple-100 p-2">
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 text-purple-600" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                        <path d="M19 21v"/>
                        <circle/>
                    </svg>
                </div>
                <span className="font-medium">Usuário</span>
            </div>
            <div>
                <button className="rounded-lg border border-purple-200 px-4 py-2 text-sm font-semibold text-purple-700 transition hover:bg-purple-50 active:bg-purple-100">Sair</button>
            </div>
        </div>
    </header>
    );
}