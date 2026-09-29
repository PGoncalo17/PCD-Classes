public class CountThread extends Thread {
    private CounterAtomic counter;

    public CountThread(CounterAtomic counter){
        this.counter = counter;
    }


    @Override 
    public void run(){
        for(int i = 0; i < 1000; i++){
            counter.increment();
        }
    }

    public static void main(String[] args){
        CounterAtomic c = new CounterAtomic();
        CountThread t1 = new CountThread(c);
        CountThread t2 = new CountThread(c);
        CountThread t3 = new CountThread(c);
        CountThread t4 = new CountThread(c);
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try{
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e){ }

        System.out.println("Final Result: " + c.getValue());
    }
}