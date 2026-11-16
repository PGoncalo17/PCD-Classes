public class MainBlockingQueue {
    public static void main(String[] args) throws InterruptedException {
        executarVersao1();
        // executarVersao2();
        // executarVersao3();
    }

    // VERSÃO 1: Número fixo de javalis
    public static void executarVersao1() throws InterruptedException {
        System.out.println("=== VERSÃO 1 (BlockingQueue) ===");
        TableBlockingQueue tableBlockingQueue = new TableBlockingQueue(10);

        Thread c1 = new Cozinheiro("Cozinheiro-1", tableBlockingQueue, 15);
        Thread c2 = new Cozinheiro("Cozinheiro-2", tableBlockingQueue, 15);
        Thread g1 = new Gaules("Obélix", tableBlockingQueue, 10);
        Thread g2 = new Gaules("Astérix", tableBlockingQueue, 10);
        Thread g3 = new Gaules("Abraracourcix", tableBlockingQueue, 10);

        c1.start(); c2.start();
        g1.start(); g2.start(); g3.start();

        c1.join(); c2.join();
        g1.join(); g2.join(); g3.join();
        System.out.println("=== FIM DA VERSÃO 1 ===");
    }

    // VERSÃO 2: Funcionamento indeterminado interrompido após 10s
    public static void executarVersao2() throws InterruptedException {
        System.out.println("=== VERSÃO 2 (10 segundos) ===");
        TableBlockingQueue tableBlockingQueue = new TableBlockingQueue(10);

        Thread[] atores = {
            new Cozinheiro("Cozinheiro-1", tableBlockingQueue, -1),
            new Cozinheiro("Cozinheiro-2", tableBlockingQueue, -1),
            new Gaules("Obélix", tableBlockingQueue, -1),
            new Gaules("Astérix", tableBlockingQueue, -1)
        };

        for (Thread t : atores) t.start();

        Thread.sleep(10000);
        System.out.println("\n[MAIN] Tempo esgotado! A interromper o banquete...\n");

        for (Thread t : atores) t.interrupt();
        for (Thread t : atores) t.join();
        System.out.println("=== FIM DA VERSÃO 2 ===");
    }

    // VERSÃO 3: Capacidade 1, 2 Cozinheiros (100), 2 Gauleses (100)
    public static void executarVersao3() throws InterruptedException {
        System.out.println("=== VERSÃO 3 (Capacidade 1) ===");
        TableBlockingQueue tableBlockingQueue = new TableBlockingQueue(1);

        Thread c1 = new Cozinheiro("Cozinheiro-1", tableBlockingQueue, 100);
        Thread c2 = new Cozinheiro("Cozinheiro-2", tableBlockingQueue, 100);
        Thread g1 = new Gaules("Obélix", tableBlockingQueue, 100);
        Thread g2 = new Gaules("Astérix", tableBlockingQueue, 100);

        c1.start(); c2.start();
        g1.start(); g2.start();

        c1.join(); c2.join();
        g1.join(); g2.join();
        System.out.println("=== FIM DA VERSÃO 3 ===");
    }
}