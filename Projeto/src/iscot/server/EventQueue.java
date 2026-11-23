package iscot.server;

import iscot.events.Event;

public class EventQueue {
    private final Event[] queue;
    private int first;
    private int last;
    private int count;

    public EventQueue(int capacity){
        this.queue = new Event[capacity];
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
    
    public synchronized int size(){
        return count;
    }


    public synchronized Event peekNextEvent() throws InterruptedException {
        while (empty()) {
            wait();
        }
        return queue[first];
    }

    public synchronized Event removeCurrentEvent(){
        if(empty()) { 
            throw new IllegalStateException("Error: Queue is empty"); 
        }
        Event value = queue[first];
        queue[first] = null;
        first = next(first);
        count --;
        notifyAll();
        return value;
    }

    public synchronized void addEvent(Event item) throws InterruptedException{
        while(count == queue.length) {
            wait();
        }
            queue[last] = item;
            last = next(last);
            count++;
            notifyAll();
    }

}
