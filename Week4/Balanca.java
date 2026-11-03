import javax.swing.JTextField;

public class Balanca{
    private double countOuro = 0.0;
    private JTextField txt;

    public Balanca(JTextField txt){
        this.txt = txt;
    }

    public double getCountOuro(){
        return countOuro;
    }

    public synchronized void addGold(double peso) throws InterruptedException{
        while(countOuro >= 12.5){
            System.out.println(Thread.currentThread().toString() + " - antes de fazer wait");
            this.wait();
            System.out.println(Thread.currentThread().toString() + " - depois de fazer wait");
        }
        countOuro += peso;
        txt.setText(String.format("%.2f Kg", countOuro));
        notifyAll();
    }

    public synchronized void removeGold() throws InterruptedException{
        while (countOuro < 12.5){
            System.out.println(Thread.currentThread().toString() + " - antes de fazer wait");
            this.wait();
            System.out.println(Thread.currentThread().toString() + " - depois de fazer wait");
        } 
        countOuro -= 12.5;
        txt.setText(String.format("%.2f Kg", countOuro));
        notifyAll();
    }
}