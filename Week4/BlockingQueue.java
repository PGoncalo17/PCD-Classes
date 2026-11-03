import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
    private final Queue<T> queue;
    private final int capacity;

    public BlockingQueue(){
        this.capacity = Integer.MAX_VALUE;
        this.queue = new LinkedList<>();
    }

    public BlockingQueue(int capacity){
        if (capacity <= 0){
            throw new IllegalArgumentException("Invalid Capacity");
        }
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    public synchronized void put(T item) throws InterruptedException{
        while(queue.size() == capacity){
            wait();
        }
        queue.add(item);
        notifyAll();
    }    
    
    
    public synchronized T take() throws InterruptedException{
        while(queue.isEmpty()){
            wait();
        }
        T item = queue.poll();
        
        if (capacity != Integer.MAX_VALUE) {
            notifyAll();
        }
    
        return item;
    }

    public synchronized int size(){
        return queue.size();
    }

    public synchronized void clear(){
        queue.clear();
        if(capacity != Integer.MAX_VALUE){
            notifyAll();
        }
    }
}