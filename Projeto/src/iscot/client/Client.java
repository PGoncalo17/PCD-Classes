package iscot.client;

import iscot.enums.Areas;
import iscot.enums.Type;
import iscot.events.Event;
import iscot.events.RegistrationEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public abstract class Client {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private  final String ip;
    private final int porto;
    private final Areas area;
    private final Type type;

    protected Client(String ip, int porto, Areas area, Type type) {
        this.ip = ip;
        this.porto = porto;
        this.area = area;
        this.type = type;
    }

    public void runClient() {
        try {
            connectToServer();
            execute();
        } catch (Exception e) { }
        finally {
            try {
                closeConnection();
            } catch (Exception e) { }
        }
    }

    public void connectToServer() throws IOException {
        socket = new Socket(ip, porto);
        System.out.print("Socket: " + socket);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        RegistrationEvent event = new RegistrationEvent(area, type);
        out.writeObject(event);
    }

    protected synchronized void sendEvent(Event event) throws IOException {
      out.writeObject(event);
      out.flush(); 
    }

    protected abstract void execute() throws IOException, ClassNotFoundException; 

    protected Event receiveEvent() throws IOException, ClassNotFoundException {
        return (Event) in.readObject();
    }

    protected synchronized void closeConnection() {
        try {
            if (in != null) socket.close(); 
            if (out != null) socket.close(); 
            if (socket != null && !socket.isClosed()) socket.close(); 
        } catch (IOException e) { }
    }

    protected Areas getArea() {
        return area;
    }

    protected Type getType() {
        return type;
    }

}