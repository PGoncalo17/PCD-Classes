package iscot.server;

public class ModifiedCountDownLatch {
    private int count;

    public ModifiedCountDownLatch(int count) {
        this.count = count;
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
        }
    }
}
