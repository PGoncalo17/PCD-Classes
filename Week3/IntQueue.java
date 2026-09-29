public class IntQueue {
    private int queue[];
    private int first;
    private int last;
    private int count;

    public IntQueue(int capacity){
        queue = new int[capacity];
        first = -1;
        last = -1;
        count = 0;
    }

    public int next(int i){
        return (i+1) % queue.length;
    }

    public boolean empty(){
        return first == -1;
    }

    public synchronized int peek(){
        if(empty()) throw new IllegalStateException("Error: Queue is empty");
        else return queue[first];
    }

    public synchronized int poll(){
        if(empty()) throw new IllegalStateException("Error: Queue is empty");
        else{
            first = next(first);
            int value = queue[first];
            count ++;
            return value;
        }
    }

    public synchronized void offer(int item){
        if(first == last) throw new IllegalStateException("Error: Queue is full");
        else{
            last = next(last);
            queue[last] = item;
            count--;
        }
    }

    public int size(){
        return count;
    }


}