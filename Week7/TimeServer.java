
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class TimeServer {
    public static final int PORTO = 2424;
    private List<DealWithTimeClients> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            new TimeServer().startServing();  
        } catch (Exception e) { }
    }

    public void startServing() throws IOException {
        Thread timeBroadcaster = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(5000);
                    TimeMessage msg = new TimeMessage(System.currentTimeMillis());
                    System.out.println("Sending time to everyone: " + msg);
                    broadcastTime(msg);
                }
            } catch (InterruptedException e) { }
        });
        timeBroadcaster.start();

        ServerSocket ss = new ServerSocket(PORTO);
        try {
            while (true) { 
                Socket socket = ss.accept();
                DealWithTimeClients handler = new DealWithTimeClients(socket, this);
                addClient(handler);
                handler.start();
            }
        } catch (Exception e) { } 
        finally {
            ss.close();
        }
    }

	public synchronized void addClient(DealWithTimeClients client) {
		clients.add(client);
        System.out.println("New client. Total: " + clients.size());
	}
	
	public synchronized void removeClient(DealWithTimeClients client) {
		clients.remove(client);
        System.out.println("Client left. Total: " + clients.size());
	}

	public synchronized void broadcastTime(TimeMessage msg) {
		for (DealWithTimeClients client : clients) {
			try {
				client.sendTime(msg);
			} catch (Exception e) { }
		}
	}
}