import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class SimpleServer {
	public static final int PORTO = 8080;
	public List<ObjectOutputStream> clientStreams = new ArrayList<>();

	public static void main(String[] args) {
		try {
			new SimpleServer().startServing();
		} catch (IOException e) { }
	}

	public void startServing() throws IOException {
		ServerSocket ss = new ServerSocket(PORTO);
		try {
			while (true) {
				Socket socket = ss.accept();
				new DealWithClients(socket, this).start();
			}
		} catch (Exception e) {
		} finally {
			ss.close();
		}
	}

	public synchronized void addClient(ObjectOutputStream out) {
		clientStreams.add(out);
	}
	
	public synchronized void removeClient(ObjectOutputStream out) {
		clientStreams.remove(out);
	}

	public synchronized void broadcast(Message msg) {
		for (ObjectOutputStream out : clientStreams) {
			try {
				out.writeObject(msg);
				out.flush();
			} catch (Exception e) { }
		}
	}
}
