import java.util.Observable;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Car extends Observable implements Runnable{
	private int id;
	private int limit;
	private int position=0;
    private static AtomicBoolean winnerFound = new AtomicBoolean(false);    // Atomic control
    private static List<Thread> carThreads = new ArrayList<>();
	
	public int getId() {
		return id;
	}

	public int getPosition() {
		return position;
	}

	public Car(int id, int limit) {
		super();
		this.id = id;
		this.limit = limit;
	}

    public static void addThread(Thread t){
        carThreads.add(t);
    }

    @Override
    public void run(){
        try{
            for(int i = 0; i <= limit; i ++){
                if(winnerFound.get()){
                    return;
                }else{
                    position = i;
                    setChanged();
                    notifyObservers();
                    Thread.sleep((long)(Math.random()*100));
                }
            }

            if(winnerFound.compareAndSet(false, true)){     // Tries to change from false to true
                for(Thread t : carThreads){
                    t.interrupt();
                }

                String msg = "Car " + (id + 1) + " won the race!";
                System.out.println(msg);
                JOptionPane.showMessageDialog(null, msg);
            }
        } catch(InterruptedException e){}
    }

}
