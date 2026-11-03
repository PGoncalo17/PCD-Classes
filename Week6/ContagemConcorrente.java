import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ContagemConcorrente {

    private static final int TARGET_INTERMEDIATE = 1_000;     
    private static final int TARGET_FINAL = 1_000_000;        

    public static void main(String[] args) {
        Thread[] group2Threads = new Thread[5];

       
        CyclicBarrier finalBarrier = new CyclicBarrier(10, () -> {
            System.out.println("Todos os threads acabaram de contar");
        });


        CyclicBarrier intermediateBarrier = new CyclicBarrier(5, () -> {
            System.out.println("Grupo 1 atingiu 10^3. A iniciar o Grupo 2...");
            for (Thread t : group2Threads) {
                t.start();
            }
        });

        for (int i = 0; i < 5; i++) {
            group2Threads[i] = new Thread(
                new CounterTask(i + 5, false, null, finalBarrier), 
                "Thread-" + (i + 5)
            );
        }

        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(
                new CounterTask(i, true, intermediateBarrier, finalBarrier), 
                "Thread-" + i
            );
            t.start();
        }
    }

    static class CounterTask implements Runnable {
        private final int id;
        private final boolean isGroup1;
        private final CyclicBarrier intermediateBarrier;
        private final CyclicBarrier finalBarrier;

        public CounterTask(int id, boolean isGroup1, CyclicBarrier intermediateBarrier, CyclicBarrier finalBarrier) {
            this.id = id;
            this.isGroup1 = isGroup1;
            this.intermediateBarrier = intermediateBarrier;
            this.finalBarrier = finalBarrier;
        }

        @Override
        public void run() {
            try {
                int count = 0;

                if (isGroup1) {
                    while (count < TARGET_INTERMEDIATE) {
                        count++;
                    }
                    intermediateBarrier.await();
                }

                while (count < TARGET_FINAL) {
                    count++;
                }

                finalBarrier.await();

            } catch (InterruptedException | BrokenBarrierException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}