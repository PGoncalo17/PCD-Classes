import java.util.concurrent.atomic.AtomicInteger;

class CounterAtomic {
    private AtomicInteger value = new AtomicInteger(0);

    public void increment(){
        value.incrementAndGet();
    }

    public int getValue(){
        return value.get();
    }
}