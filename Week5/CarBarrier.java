import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;

public class CarBarrier extends Observable implements Runnable{
	private int id;
	private int limit;
	private int position=0;
    private static AtomicInteger arrivalOrder = new AtomicInteger(1);    // Atomic control
    private static CyclicBarrier barrier;
	
	public int getId() {
		return id;
	}

	public int getPosition() {
		return position;
	}

    public static void initRace(int numCars) {
        arrivalOrder.set(1);
        barrier = new CyclicBarrier(numCars);
    }

	public CarBarrier(int id, int limit) {
		super();
		this.id = id;
		this.limit = limit;
	}

    public int arrive() throws InterruptedException, BrokenBarrierException {
        int place = arrivalOrder.getAndIncrement();
        barrier.await();
        return place;
    }


    @Override
    public void run(){
        try {     
            for (int i = 0; i <= limit; i++) {
                position = i;
                setChanged();
                notifyObservers();
                Thread.sleep((long) (Math.random() * 100));
            }

            System.out.println("Arrived: Car [id=" + id + "]");

            int place = arrive();

            System.out.println("Car [id=" + id + "] ficou em " + place + "º");
        } catch(InterruptedException | BrokenBarrierException e){
            Thread.currentThread().interrupt();
        }
    }

}
