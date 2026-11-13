public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Descomenta a versão que queres testar:
        executarVersao1();
        // executarVersao2();
        // executarVersao3();
    }

    // VERSÃO 1: Número fixo de javalis
    public static void executarVersao1() throws InterruptedException {
        System.out.println("=== VERSÃO 1 ===");
        Table table = new Table(10, false);
        
        Thread c1 = new Cozinheiro("Cozinheiro-1", table, 15);
        Thread c2 = new Cozinheiro("Cozinheiro-2", table, 15);
        Thread g1 = new Gaules("Obélix", table, 10);
        Thread g2 = new Gaules("Astérix", table, 10);
        Thread g3 = new Gaules("Abraracourcix", table, 10);

        c1.start(); c2.start();
        g1.start(); g2.start(); g3.start();

        c1.join(); c2.join();
        g1.join(); g2.join(); g3.join();
        System.out.println("=== FIM DA VERSÃO 1 ===");
    }

    // VERSÃO 2: Funcionamento indeterminado interrompido após 10s
    public static void executarVersao2() throws InterruptedException {
        System.out.println("=== VERSÃO 2 (10 segundos) ===");
        Table table = new Table(10, false);

        Thread[] atores = {
            new Cozinheiro("Cozinheiro-1", table, -1),
            new Cozinheiro("Cozinheiro-2", table, -1),
            new Gaules("Obélix", table, -1),
            new Gaules("Astérix", table, -1)
        };

        for (Thread t : atores) t.start();

        Thread.sleep(10000); // Aguarda 10 segundos
        System.out.println("\n[MAIN] Tempo esgotado! A interromper o banquete...\n");

        for (Thread t : atores) t.interrupt();
        for (Thread t : atores) t.join();
        
        System.out.println("=== FIM DA VERSÃO 2 ===");
    }

    // VERSÃO 3: 2 Cozinheiros (100), 2 Gauleses (100), Table capacidade 1, usando signal()
    public static void executarVersao3() throws InterruptedException {
        System.out.println("=== VERSÃO 3 (Risco de Deadlock) ===");
        Table table = new Table(1, true); // Capacidade 1 + modoBugVersao3 = true

        Thread c1 = new Cozinheiro("Cozinheiro-1", table, 100);
        Thread c2 = new Cozinheiro("Cozinheiro-2", table, 100);
        Thread g1 = new Gaules("Obélix", table, 100);
        Thread g2 = new Gaules("Astérix", table, 100);

        c1.start(); c2.start();
        g1.start(); g2.start();

        c1.join(); c2.join();
        g1.join(); g2.join();
        System.out.println("=== FIM DA VERSÃO 3 ===");
    }
}