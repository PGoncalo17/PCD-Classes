import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TableBlockingQueue  implements ITable{
    private final BlockingQueue<Javali> table;

    public TableBlockingQueue(int capacidade) {
        this.table = new ArrayBlockingQueue<>(capacidade);        
    }

    @Override
    public void addFood (Javali javali) throws InterruptedException {
        table.put(javali);
        System.out.println("[+] " + Thread.currentThread().getName() + " colocou " + javali + " | Total na mesa: " + table.size());
    }

    @Override
    public Javali serveFood() throws InterruptedException {
        Javali j = table.take();
        System.out.println("[-] " + Thread.currentThread().getName() + " retirou " + j + " | Total na mesa: " + table.size());
        return j;
    }
}