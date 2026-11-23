package iscot.server;

import iscot.enums.Type;
import iscot.enums.Areas;
import iscot.events.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static int PORTO;
    private int nextClientId = 1;
    private final Map<String, EventsChannel> channels = new HashMap<>();
    private static final int waitPeriod = 5000;

    public Server() {
        for (Type type : Type.values()) {
            for (Areas area : Areas.values()) {
                String key = generateKey(type, area);
                EventsChannel channel = new EventsChannel(key, waitPeriod);
                channels.put(generateKey(type, area), channel);
                channel.start();
            }
        }
    }

    private String generateKey(Type type, Areas area) {
        return type.name() + "_" + area.name();
    }


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
        System.out.println("[CLIENTE REGISTADO] ID: " + client.getClientId());

        EventsChannel channel = channels.get(generateKey(client.getClientType(), client.getClientArea()));
        if (channel != null) {
            channel.addInterestedClient(client);
        }
    }

    public synchronized void removeClient(DealWithClients client) {
            System.out.println("[CLIENTE DESLIGADO] ID: " + client.getClientId());

            EventsChannel channel = channels.get(generateKey(client.getClientType(), client.getClientArea()));
            if (channel != null) {
                channel.removeInterestedClient(client);
            }
    }

    // Encaminhamento base (será substituído pelas filas e CountDownLatch nas fases 6 e 7)
    public synchronized void routeEvent(Event event) {
       EventsChannel destinatedChannel = null;

        if (event instanceof SensorEvent) {
            destinatedChannel = channels.get(generateKey(Type.DECIDER, event.getArea()));
        } 
        else if (event instanceof HighLevelOrderEvent) {
            destinatedChannel = channels.get(generateKey(Type.ACTUATOR, event.getArea()));
        }

        if (destinatedChannel != null) {
            try {
                destinatedChannel.publishEvent(event);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
                
}