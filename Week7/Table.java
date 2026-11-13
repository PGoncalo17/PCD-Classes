
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Table implements ITable{

    private final Queue<Javali> javalis = new LinkedList<>();
    private int capacidade;
    private final ReentrantLock lock = new ReentrantLock();

    private final Condition salaEsperaCozinheiros = lock.newCondition();
    private final Condition salaEsperaGauleses = lock.newCondition();

    private final Condition condicaoUnica = lock.newCondition();
    private final boolean modoBugVersao3;

    public Table(int capacidade, boolean modoBugVersao3){
        this.capacidade = capacidade;
        this.modoBugVersao3 = modoBugVersao3;
    }



    public void addFood(Javali javali) throws InterruptedException {
        lock.lock();
        try {
            while (javalis.size() == capacidade) {
                System.out.println("[MESA CHEIA] " + Thread.currentThread().getName() + " à espera de espaço...");
                if (modoBugVersao3) {
                    condicaoUnica.await();
                } else {
                    salaEsperaCozinheiros.await();
                }
            }
            javalis.add(javali);
            System.out.println("[+] " + Thread.currentThread().getName() + " colocou " + javali + " | Total na mesa: " + javalis.size());

            if (modoBugVersao3) {
                condicaoUnica.signal();
            } else {
                salaEsperaGauleses.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public Javali serveFood() throws InterruptedException {
        lock.lock();
        try {
            while (javalis.isEmpty()){
                System.out.println("[MESA VAZIA] " + Thread.currentThread().getName() + " à espera de comida...");
                if (modoBugVersao3) {
                    condicaoUnica.await();
                } else {
                    salaEsperaGauleses.await();
                }
            }
            Javali j = javalis.poll();
            System.out.println("[-] Javali servido com sucesso");
            if (modoBugVersao3) {
                condicaoUnica.signal();
            } else {
                salaEsperaCozinheiros.signalAll();
            }
            return j;
        } finally {
            lock.unlock();
        }
    }
}
