package iscot.server;

import iscot.enums.Areas;
import iscot.enums.Type;
import iscot.events.Event;
import iscot.events.ReceptionConfirmationMessage;
import iscot.events.RegistrationEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class DealWithClients extends Thread{
    private final Socket socket;
    private final Server server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int clientId;
    private Type clientType;
    private Areas clientArea;

    public DealWithClients(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public int getClientId() {
        return clientId;
    }

    public Type getClientType() {
        return clientType;
    }

    public Areas getClientArea() {
        return clientArea;
    }

    @Override
    public void run() {
        try {
            doConnections(socket);
            if(!handleRegistration()) {
                System.err.println("Registo inválido. A recusar ligação...");
                return;
            }
            server.addClient(this);
            serve();
        } catch (EOFException | SocketException e) { 
            System.out.println("Ligação encerrada pelo cliente (ID: " + clientId + ").");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro na comunicação com o cliente (ID: " + clientId + ").");
        }
        finally {
            server.removeClient(this);
            closeResources();
        }
    }


    public void doConnections(Socket socket) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
    }

    public boolean handleRegistration() throws IOException, ClassNotFoundException {
        Object firstObj = in.readObject();

        if (!(firstObj instanceof RegistrationEvent)) {
            return false;
        }

        RegistrationEvent reg = (RegistrationEvent) firstObj;

        if(reg.getType() == null || reg.getArea() == null) {
            return false;
        }

        this.clientId = server.generateClientId();
        this.clientType = reg.getType();
        this.clientArea = reg.getArea();
        return true;
    }

    public void serve() throws IOException, ClassNotFoundException {
        while (true) { 
            Object obj = in.readObject();
            if (obj instanceof Event) {
                Event event = (Event) obj;
                System.out.println("Recebido de ID " + clientId + ": " + event);
                server.routeEvent(event);
            } else if (obj instanceof ReceptionConfirmationMessage) {
                ReceptionConfirmationMessage msg = (ReceptionConfirmationMessage) obj;
                System.out.println("Confirmação recebida de ID " + clientId + ": " + msg);
                server.handleConfirmation(this.clientArea, msg);
            }
        }
    }


    public synchronized void sendEvent(Event event) throws IOException {
        out.writeObject(event);
        out.flush();
    }

    
    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) { }
    }

}