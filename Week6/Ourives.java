

public class Ourives extends Thread{
    private int totalLingotes = 0;
    private Balanca balanca;

    public Ourives(Balanca balanca){
        this.balanca = balanca;
    }

    @Override 
    public void run(){
        System.out.println(Thread.currentThread().toString() + " - inicio do run");
        while(!Thread.currentThread().isInterrupted()){
            try {
                balanca.removeGold();
                System.out.println(Thread.currentThread().toString() + " - antes de fazer sleep");
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().toString() + " - depois de fazer sleep");
                totalLingotes ++;
            } catch (Exception e) {System.out.println(Thread.currentThread().toString() + " - antes de tratar InterruptedException"); break; }
        }
        System.out.println("Total de lingotes criados: " + totalLingotes);
        System.out.println(Thread.currentThread().toString() + " - fim do run");
    }

}