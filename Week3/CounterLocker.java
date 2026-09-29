import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class CounterLocker {
    private int value = 0;
    private final Lock lock = new ReentrantLock();

    public synchronized void increment(){
        lock.lock();
        try{
            value++;
        } finally {
            lock.unlock();
        }
    }

    public synchronized int getValue(){
        return value;
    }
}