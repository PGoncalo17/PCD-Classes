package iscot.server;

public class ModifiedCountdownLatch {
    private int count;

    public ModifiedCountdownLatch(int count) {
        this.count = count;
    }

    public synchronized int getCount() {
        return count;
    }

    public synchronized void countdown() {
        if (count > 0) {
            count --;
            if (count == 0) {
                notifyAll();
            }
        }
    }

    public synchronized void await(int waitPeriod) throws InterruptedException {
        long endTime = System.currentTimeMillis() + waitPeriod;
        while (count > 0) {
            long remaining = endTime - System.currentTimeMillis();
            if (remaining <= 0) {
                break;
            }
            wait(remaining);
        }
    }
}
