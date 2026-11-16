import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;
import java.net.SocketTimeoutException;

public class DealWithTimeClients extends Thread{
    private Socket socket;
    private TimeServer server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private TimeMessage pendingTime = null;

    public DealWithTimeClients(Socket socket, TimeServer server) {
        this.socket = socket;
        this.server = server;
    }

    public synchronized void sendTime(TimeMessage msg) {
        this.pendingTime = msg;
        notifyAll();
    }

    @Override
    public void run() {
        try {
            doConnections(socket);
            serve();
        } catch (SocketTimeoutException e) {
            System.out.println("Timeout: Client didn't respond in 2 seconds");
         } catch (Exception e) {
            System.out.println("Client turned off");
         }
        finally {
                server.removeClient(this);
            try {
                socket.close();
            } catch (IOException e) { }
        }
    }

    private void doConnections(Socket socket) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void serve() throws Exception {
        while (true) {
            TimeMessage msgToSend;
            synchronized (this) {
                while (pendingTime == null) {
                    wait();
                }
                msgToSend = pendingTime;
                pendingTime = null;
            }
            
            out.writeObject(msgToSend);
            out.flush();
            socket.setSoTimeout(2000);
            ReceptionConfirmationMessage rcvMsg = (ReceptionConfirmationMessage) in.readObject();
            System.out.println("Received confirmation: " + rcvMsg);
        }
    }

}