
export default function Home() {
  return (
    <div className="min-h-screen bg-slate-50 text-slate-800 font-sans">
      
      {/* HEADER / NAVEGAÇÃO */}
      <header className="fixed top-0 left-0 right-0 z-50 bg-white/80 backdrop-blur-md border-b border-purple-100">
        <div className="max-w-7xl mx-auto px-6 h-20 flex items-center justify-between">
          
          {/* Logo */}
          <div className="flex items-center gap-2 cursor-pointer">
            <div className="w-10 h-10 rounded-xl bg-gradient-to-tr from-purple-600 to-purple-400 flex items-center justify-center text-white font-bold text-xl shadow-md shadow-purple-200">
              SA
            </div>
            <span className="text-xl font-extrabold bg-gradient-to-r from-purple-700 to-purple-500 bg-clip-text text-transparent">
              SolidaryAction
            </span>
          </div>

          {/* Navegação e Login */}
          <div className="flex items-center gap-6">
            <nav className="hidden md:flex items-center gap-6 text-sm font-medium text-slate-600">
              <a href="#sobre" className="hover:text-purple-600 transition">Nossa História</a>
              <a href="#impacto" className="hover:text-purple-600 transition">Impacto</a>
              <a href="#ongs" className="hover:text-purple-600 transition">Para ONGs</a>
            </nav>
            <a 
              href="/login" 
              className="px-5 py-2.5 rounded-xl border border-purple-200 text-purple-700 font-semibold text-sm hover:bg-purple-50 transition shadow-sm"
            >
              Entrar / Login
            </a>
          </div>

        </div>
      </header>

      {/* HERO SECTION */}
      <section className="pt-32 pb-20 md:pt-44 md:pb-32 bg-gradient-to-b from-purple-50/50 via-white to-white overflow-hidden">
        <div className="max-w-7xl mx-auto px-6 grid grid-cols-1 md:grid-cols-2 gap-12 items-center">
          
          <div className="space-y-6">
            <span className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-purple-100 text-purple-700 text-xs font-bold tracking-wide uppercase">
              ✨ Transformando doações em impacto real
            </span>
            <h1 className="text-4xl md:text-6xl font-black tracking-tight text-slate-900 leading-tight">
              Conectando <span className="text-purple-600">corações</span> a causas que transformam vidas.
            </h1>
            <p className="text-lg text-slate-600">
              A <strong>SolidaryAction</strong> é a plataforma definitiva para doações e ONGs. Nossa tecnologia contabiliza, audita e exibe em tempo real cada centavo arrecadado para a sua causa.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 pt-2">
              <button className="px-8 py-4 rounded-xl bg-green-400 hover:bg-green-500 text-slate-900 font-bold text-base transition shadow-lg shadow-green-400/20 text-center cursor-pointer">
                Quero Doar Agora
              </button>
              <button className="px-8 py-4 rounded-xl bg-purple-600 hover:bg-purple-700 text-white font-bold text-base transition shadow-lg shadow-purple-600/20 text-center cursor-pointer">
                Cadastrar minha ONG
              </button>
            </div>
          </div>

          {/* Card Flutuante / Preview do Sistema */}
          <div className="relative">
            <div className="absolute -inset-1 rounded-2xl bg-gradient-to-r from-purple-600 to-green-400 opacity-30 blur-xl"></div>
            <div className="relative bg-white p-8 rounded-2xl border border-purple-100 shadow-xl space-y-6">
              <div className="flex justify-between items-center border-b border-slate-100 pb-4">
                <div>
                  <p className="text-xs text-slate-400 uppercase font-semibold">Campanha em Destaque</p>
                  <h3 className="font-bold text-slate-800 text-lg">SOS Inverno Solidário</h3>
                </div>
                <span className="px-3 py-1 bg-green-100 text-green-700 text-xs font-bold rounded-full">Ativa</span>
              </div>
              
              <div className="space-y-2">
                <div className="flex justify-between text-sm">
                  <span className="text-slate-600 font-medium">Total Arrecadado</span>
                  <span className="font-bold text-purple-700 text-lg">R$ 142.500,00</span>
                </div>
                <div className="w-full bg-slate-100 h-3 rounded-full overflow-hidden">
                  <div className="bg-gradient-to-r from-purple-600 to-green-400 h-full rounded-full w-[85%]"></div>
                </div>
                <p className="text-xs text-right text-slate-400">Meta: R$ 160.000,00 (85%)</p>
              </div>

              <div className="bg-purple-50/50 p-4 rounded-xl border border-purple-100/50 flex items-center justify-between">
                <div className="flex items-center gap-3">
                  <div className="w-8 h-8 rounded-full bg-purple-200 flex items-center justify-center text-purple-700 font-bold text-xs">
                    ONG
                  </div>
                  <div>
                    <p className="text-xs font-semibold text-slate-700">Instituto Esperança</p>
                    <p className="text-[10px] text-slate-500">1.420 doadores conectados</p>
                  </div>
                </div>
                <span className="text-xs font-bold text-purple-600 bg-white px-3 py-1.5 rounded-lg border border-purple-100 shadow-xs">
                  Ver Histórico
                </span>
              </div>
            </div>
          </div>

        </div>
      </section>

      {/* NOSSA HISTÓRIA / SOBRE */}
      <section id="sobre" className="py-24 bg-white border-t border-slate-100">
        <div className="max-w-5xl mx-auto px-6 text-center space-y-12">
          <div className="space-y-4">
            <h2 className="text-xs font-extrabold uppercase tracking-widest text-purple-600">Nossa História</h2>
            <h3 className="text-3xl md:text-4xl font-extrabold text-slate-900">
              Como nasceu a SolidaryAction?
            </h3>
            <p className="text-slate-600 max-w-2xl mx-auto text-lg leading-relaxed">
              Nascemos da necessidade de trazer <strong>transparência radical</strong> para o terceiro setor. Percebíamos que muitas ONGs sofriam para comprovar seus números e os doadores queriam ver o impacto real do seu dinheiro. Criamos um sistema automatizado que contabiliza cada doação em tempo real, unindo quem quer ajudar com quem realmente faz acontecer.
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 pt-6">
            <div className="p-6 rounded-2xl bg-purple-50/50 border border-purple-100 space-y-3">
              <div className="w-12 h-12 rounded-xl bg-purple-600 text-white font-bold flex items-center justify-center mx-auto text-xl shadow-md">
                01
              </div>
              <h4 className="font-bold text-slate-800 text-lg">Contabilização Real</h4>
              <p className="text-sm text-slate-600">O sistema calcula automaticamente cada entrada de doação vinculada à ONG correspondente.</p>
            </div>
            
            <div className="p-6 rounded-2xl bg-purple-50/50 border border-purple-100 space-y-3">
              <div className="w-12 h-12 rounded-xl bg-purple-600 text-white font-bold flex items-center justify-center mx-auto text-xl shadow-md">
                02
              </div>
              <h4 className="font-bold text-slate-800 text-lg">Gestão para ONGs</h4>
              <p className="text-sm text-slate-600">Painel completo para instituições acompanharem metas, campanhas e apoiadores de forma simples.</p>
            </div>

            <div className="p-6 rounded-2xl bg-purple-50/50 border border-purple-100 space-y-3">
              <div className="w-12 h-12 rounded-xl bg-green-400 text-slate-900 font-bold flex items-center justify-center mx-auto text-xl shadow-md">
                03
              </div>
              <h4 className="font-bold text-slate-800 text-lg">Segurança Total</h4>
              <p className="text-sm text-slate-600">Transparência de ponta a ponta para garantir que a sua doação chegue onde precisa com total segurança.</p>
            </div>
          </div>
        </div>
      </section>

      {/* FOOTER */}
      <footer className="bg-slate-900 text-white py-12 border-t border-slate-800">
        <div className="max-w-7xl mx-auto px-6 flex flex-col md:flex-row items-center justify-between gap-6">
          <div className="flex items-center gap-2">
            <div className="w-8 h-8 rounded-lg bg-purple-600 flex items-center justify-center text-white font-bold text-sm">
              SA
            </div>
            <span className="font-bold text-lg tracking-wide">SolidaryAction</span>
          </div>
          <p className="text-sm text-slate-400">
            © 2026 SolidaryAction. Todos os direitos reservados.
          </p>
          <div className="flex gap-4 text-sm text-slate-400">
            <a href="#" className="hover:text-white transition">Termos</a>
            <a href="#" className="hover:text-white transition">Privacidade</a>
            <a href="#" className="hover:text-white transition">Contato</a>
          </div>
        </div>
      </footer>

    </div>
  );
}
