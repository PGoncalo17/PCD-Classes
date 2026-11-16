public class Cozinheiro extends Thread {
    private final ITable table;
    private final int maxJavalis;

    public Cozinheiro(String nome, ITable table, int maxJavalis){
        super(nome);
        this.table = table;
        this.maxJavalis = maxJavalis;
    }

    @Override 
    public void run(){
        int count = 1;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (maxJavalis > 0 && count > maxJavalis) {
                    break;
                }
                Thread.sleep((long) (Math.random() * 50));
                Javali j = new Javali(getName(), count++);
                table.addFood(j);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}