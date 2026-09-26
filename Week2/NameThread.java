import java.util.Random;

public class NameThread extends Thread {
    private int id;

    public NameThread(int id){
        this.id = id;
    }
    
    @Override 
    public void run(){
        try{
            while(!Thread.currentThread().isInterrupted()){
                long n = 1000 + (long)(Math.random()*1000);
                System.out.println(id);
                sleep(n);
            }
        } catch(InterruptedException e){}
    }

    public static void main(String[] args){
        NameThread t1 = new NameThread(1);
        NameThread t2 = new NameThread(2);
        t1.start();
        t2.start();
            try{
                Thread.sleep(4000);
                t1.interrupt();
                t2.interrupt();
                t1.join();
                t2.join();
                System.out.println("End of program.");
            } catch (InterruptedException e){ }

    }

}