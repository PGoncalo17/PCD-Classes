package iscot.server;

import iscot.enums.Type;
import iscot.events.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static int PORTO;
    public List<DealWithClients> clients = new ArrayList<>();
    private int nextClientId = 1;


    public static void main (String[] args) {
        System.out.println("Inicializing Server...");
        PORTO = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        try {
            new Server().startServing();
        } catch (IOException e) { 
            System.out.println("Erro ao iniciar o servidor no porto " + PORTO);
        }
    }


    public void startServing() throws IOException {
        ServerSocket ss = new ServerSocket(PORTO);
        try {
            while (true) {
                Socket socket = ss.accept();
                new DealWithClients(socket, this).start();
            }
        } catch (Exception e) { }
        finally {
            ss.close();
        }
    }

    public synchronized int generateClientId() {
        return nextClientId ++;
    }

    public synchronized void addClient(DealWithClients client) {
        clients.add(client);
        System.out.println("[CLIENTE REGISTADO] ID: " + client.getClientId());
    }

    public synchronized void removeClient(DealWithClients client) {
        if (clients.remove(client)) {
            System.out.println("[CLIENTE DESLIGADO] ID: " + client.getClientId());
        }
    }

    // Encaminhamento base (será substituído pelas filas e CountDownLatch nas fases 6 e 7)
    public synchronized void routeEvent(Event event) {
        for (DealWithClients client : clients) {
            try {
                // Sensores enviam para Decisores (da mesma área)
                if (event instanceof SensorEvent && client.getClientType() == Type.DECIDER) {
                    if (client.getClientArea() == event.getArea()) {
                        client.sendEvent(event);
                    }
                } 
                // Decisores enviam ordens para Atuadores (da mesma área)
                else if (event instanceof HighLevelOrderEvent && client.getClientType() == Type.ACTUATOR) {
                    if (client.getClientArea() == event.getArea()) {
                        client.sendEvent(event);
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao enviar evento para o cliente " + client.getClientId());
                e.printStackTrace();
            }
        }
    }
                
}