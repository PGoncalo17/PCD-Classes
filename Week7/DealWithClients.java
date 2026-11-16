
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DealWithClients extends Thread {
    private Socket socket;
    private SimpleServer server;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public DealWithClients(Socket socket, SimpleServer server){
        this.socket = socket;
        this.server = server;
    }

    @Override 
    public void run() {
        try {
            doConnections(socket);
            server.addClient(out);
            serve();
        } catch (Exception e) { }     
        finally {
            if (out != null) {
                server.removeClient(out);
            }
            try {
                socket.close();
            } catch (Exception e) { }
        }
    }

    private void doConnections(Socket socket) throws IOException {
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	private void serve() throws IOException, ClassNotFoundException {
		while (true) {
			Message msg = (Message) in.readObject();
			if (msg.equals("FIM"))
				break;
			System.out.println("Broadcast: " + msg);
			server.broadcast(msg);
		}
	}
}