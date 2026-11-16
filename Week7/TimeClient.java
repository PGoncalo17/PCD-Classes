
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;



public class TimeClient {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

    public static void main(String[] args) {
        new TimeClient().runClient();
    }

    
    public void runClient() {
        try {
            connectToServer();
            while (true) { 
                TimeMessage msg = (TimeMessage) in.readObject();
                System.out.println("Received Time: " + msg);
                out.writeObject(new ReceptionConfirmationMessage("Time received."));
                out.flush();
            }
        } catch (Exception e) {
            System.out.println("Connection lost: " + e.getMessage());
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) { }
        }
    }


    private void connectToServer() throws IOException {
        InetAddress endereco = InetAddress.getByName(null);
        socket = new Socket(endereco, TimeServer.PORTO);
        System.out.println("Socket: " + socket);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
    }
    
}