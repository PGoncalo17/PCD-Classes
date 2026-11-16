import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;

public class Balanca{
    private double countOuro = 0.0;
    private JTextField txt;

    private ReentrantLock lock = new ReentrantLock();
    private Condition waitingOurives = lock.newCondition();
    private Condition waitingEscavadora = lock.newCondition();


    public Balanca(JTextField txt){
        this.txt = txt;
    }

    public double getCountOuro(){
        return countOuro;
    }

    public void addGold(double peso) throws InterruptedException{
        lock.lock();
        try {
            while(countOuro >= 12.5){
                System.out.println(Thread.currentThread().toString() + " - antes de fazer wait");
                waitingEscavadora.await();
                System.out.println(Thread.currentThread().toString() + " - depois de fazer wait");
            }
            countOuro += peso;
            txt.setText(String.format("%.2f Kg", countOuro));
            waitingOurives.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void removeGold() throws InterruptedException{
        lock.lock();
        try {
            while (countOuro < 12.5){
                System.out.println(Thread.currentThread().toString() + " - antes de fazer wait");
                waitingOurives.await();
                System.out.println(Thread.currentThread().toString() + " - depois de fazer wait");
            } 
            countOuro -= 12.5;
            txt.setText(String.format("%.2f Kg", countOuro));
            waitingEscavadora.signalAll();
        } finally {
            lock.unlock();
        }
    }
}