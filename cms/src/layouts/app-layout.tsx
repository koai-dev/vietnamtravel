import { Header } from "@/components/header";
import { Sidebar } from "@/components/sidebar";

export function AppLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex h-screen">
      <Sidebar />
      <main className="flex-1">
        <Header />
        <div className="p-8">{children}</div>
      </main>
    </div>
  );
}
