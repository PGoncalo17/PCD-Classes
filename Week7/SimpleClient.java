import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class SimpleClient {
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;

	
	public static void main(String[] args) {
		new SimpleClient().runClient();
	}

	public void runClient() {
		try {
			connectToServer();
			Thread readerThread = new Thread(() -> {
				try {
					while (true) {
						Message msg = (Message) in.readObject();
						System.out.println("Recebido: " + msg);
					}
				} catch (Exception e) { }
			});
			readerThread.start();
			sendMessages();
		} catch (IOException e) {
		} finally {
			try {
				if (socket != null) socket.close();
			} catch (IOException e) { }
		}
	}

	private void connectToServer() throws IOException {
		InetAddress endereco = InetAddress.getByName(null);
		socket = new Socket(endereco, SimpleServer.PORTO);
		System.out.println("Socket: " + socket);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	private void sendMessages() throws IOException {
		for (int i = 0; i < 10; i++) {
			out.writeObject(new Message("Ola " + i));
			out.flush();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {//...  
			}
		}
		out.writeObject(new Message("FIM"));
		out.flush();
	}

}
