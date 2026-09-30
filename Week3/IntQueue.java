public class IntQueue {
    private int queue[];
    private int first;
    private int last;
    private int count;

    public IntQueue(int capacity){
        this.queue = new int[capacity];
        this.first = 0;
        this.last = 0;
        this.count = 0;
    }

    public int next(int i){
        return (i+1) % queue.length;
    }

    public synchronized boolean empty(){
        return count == 0;
    }

    public synchronized int peek(){
        if(empty()) throw new IllegalStateException("Error: Queue is empty");
        else return queue[first];
    }

    public synchronized int poll(){
        if(empty()) throw new IllegalStateException("Error: Queue is empty");
        else{
            int value = queue[first];
            first = next(first);
            count --;
            return value;
        }
    }

    public synchronized void offer(int item){
        if(count == queue.length) throw new IllegalStateException("Error: Queue is full");
        else{
            queue[last] = item;
            last = next(last);
            count++;
        }
    }

    public synchronized int size(){
        return count;
    }


}