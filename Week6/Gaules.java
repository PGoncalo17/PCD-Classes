class Gaules extends Thread {
    private final ITable table;
    private final int maxJavalis; // Se <= 0, funciona indeterminadamente

    public Gaules(String nome, ITable table, int maxJavalis) {
        super(nome);
        this.table = table;
        this.maxJavalis = maxJavalis;
    }

    @Override
    public void run() {
        int consumidos = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (maxJavalis > 0 && consumidos >= maxJavalis) {
                    break;
                }
                table.serveFood();
                consumidos++;
                Thread.sleep((long) (Math.random() * 70)); // Simula tempo a comer
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(">>> " + getName() + " ficou satisfeito e saiu do banquete.");
    }
}