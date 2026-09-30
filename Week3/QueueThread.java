
public class QueueThread extends Thread {
    private IntQueue queue;
    private int threadId;
    private static int ItemsPerThread = 2000;

    public QueueThread(IntQueue queue, int threadId){
        this.queue = queue;
        this.threadId = threadId;
    }

    @Override 
    public void run(){
        for(int i = 1; i <= ItemsPerThread; i++){
            int item = (threadId * 10000) + i;
            queue.offer(item);
        }
    }

    public static void main(String[] args){
        int numThreads = 6;
        int totalExpectedItem = numThreads * ItemsPerThread;

        IntQueue queue = new IntQueue(totalExpectedItem);
        QueueThread[] threads = new QueueThread[numThreads];

        for(int i = 0; i < numThreads; i++){
            threads[i] = new QueueThread(queue, i + 1);
            threads[i].start();
        }

        for(int i = 0; i < numThreads; i++){
            try{
                threads[i].join();
            } catch (InterruptedException e){}
        }

        System.out.println("Final queue length: " + queue.size());
        System.out.println("Expected length: " + totalExpectedItem);

    }
}