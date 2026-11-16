

public class Escavadora extends Thread{
    private Balanca balanca;

    public Escavadora(Balanca balanca){
        this.balanca = balanca;
    }

    @Override 
    public void run(){
        System.out.println(Thread.currentThread().toString() + " - inicio do run");
        while(!Thread.currentThread().isInterrupted()){
            double ouro = Math.random();
            try {
                balanca.addGold(ouro);       
                System.out.println(Thread.currentThread().toString() + " - adicionou " + String.format("%.2f", ouro) + " kg");

                System.out.println(Thread.currentThread().toString() + " - antes de fazer sleep");
                Thread.sleep(300); 
                System.out.println(Thread.currentThread().toString() + " - depois de fazer sleep");
            } catch (Exception e) { System.out.println(Thread.currentThread().toString() + " - antes de tratar InterruptedException"); break;}
        }
        System.out.println(Thread.currentThread().toString() + " - fim do run");
    }

}