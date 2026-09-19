import Footer from "../components/Footer";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";

export default function
SistemaLayout({children}){
    return(
        <div className="flex min-h-screen bg-purple-50">
            <Sidebar/>
            <div className="flex flex-1 flex-col min-w-0">
                <Header/>
                <main className="flex-1 overflow-y-auto p-4 md:p-8">
                    {children}
                </main>
                <Footer/>
            </div>
        </div>
        );
}